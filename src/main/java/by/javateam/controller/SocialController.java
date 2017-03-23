package by.javateam.controller;

import by.javateam.model.FacebookUser;
import by.javateam.model.InstagramUser;
import by.javateam.service.SocialService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitorjbl.json.JsonViewModule;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.exceptions.InstagramException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class SocialController {

    private final static String CLIENT_ID_FOR_INSTAGRAM = "0e37d7d0c3534a14a6d8448cdf5cef71";
    private final static String CLIENT_ID_SECRET_FOR_INSTAGRAM = "629b16ba96a44b8db7ad34bbb54344aa";
    private final static String REDIRECT_URI = "http://user-app-team.herokuapp.com/api/callback/instagram";
    private final static String FACEBOOK = "facebook";
    private final static String FACEBOOK_ACCESS_TOKEN = "facebookAccessToken";
    private final static String CURRENT_USER_FACEBOOK = "currentUserFacebook";
    private final static String CURRENT_USER_INSTAGRAM = "currentUserInstagram";
    private Environment environment;
    private ConnectionFactoryRegistry connectionFactoryRegistry;
    private OAuth2Parameters oAuth2Parameters;
    private SocialService socialService;
    @Autowired
    public SocialController(final Environment environment,
                            final ConnectionFactoryRegistry connectionFactoryRegistry,
                            final OAuth2Parameters oAuth2Parameters,
                            final SocialService socialService) {
        this.environment = environment;
        this.connectionFactoryRegistry = connectionFactoryRegistry;
        this.oAuth2Parameters = oAuth2Parameters;
        this.socialService = socialService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    /**
     * Starts Instagram login flow
     *
     * @return redirect to Instagram login page
     */
    @RequestMapping(value = "/login/instagram", method = RequestMethod.GET)
    public ModelAndView loginInstagram() {
        String uri = "https://api.instagram.com/oauth/authorize/?";
        String clientId = "client_id=";
        String redirectUri = "redirect_uri=";
        String code = "response_type=code";
        String authorizeUrl = uri +
                clientId + CLIENT_ID_FOR_INSTAGRAM +
                "&" +
                redirectUri + REDIRECT_URI +
                "&" +
                code;
        RedirectView redirectView = new RedirectView(authorizeUrl, true, true,
                true);

        return new ModelAndView(redirectView);
    }

    /**
     * Starts Facebook login flow
     *
     * @return redirect to Facebook login page
     */
    @RequestMapping(value = "/login/facebook", method = RequestMethod.GET)
    public ModelAndView loginFacebook() {
        FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory) connectionFactoryRegistry
                .getConnectionFactory(FACEBOOK);
        OAuth2Operations oauthOperations = facebookConnectionFactory
                .getOAuthOperations();
        oAuth2Parameters.setState("user-app-team");
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(oAuth2Parameters);
        RedirectView redirectView = new RedirectView(authorizeUrl, true, true,
                true);

        return new ModelAndView(redirectView);
    }

    /**
     * Exchange Facebook code for Token,
     * save user in database,
     * add user in session,
     * when user logged in with Facebook
     *
     * @param code    Facebook authorization code
     * @param state   application state for security
     * @param request HttpServletRequest
     * @return redirect to main page
     */
    @RequestMapping(value = "/callback/facebook", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView callbackFromFacebook(
            final @RequestParam("code") String code,
            final @RequestParam("state") String state,
            final HttpServletRequest request) {

        FacebookConnectionFactory connectionFactory = (FacebookConnectionFactory) connectionFactoryRegistry
                .getConnectionFactory(FACEBOOK);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        AccessGrant accessGrant = oauthOperations.exchangeForAccess(
                code,
                environment.getProperty("facebook.callback"),
                null);
        HttpSession session = request.getSession();
        session.setAttribute(FACEBOOK_ACCESS_TOKEN, accessGrant.getAccessToken());
        User user = new FacebookTemplate(accessGrant.getAccessToken()).userOperations().getUserProfile();
        FacebookUser facebookUser = new FacebookUser(user.getId(), user.getName());
        socialService.saveFacebookUser(facebookUser);
        Map<String, String> currentUser = new HashMap<String, String>();
        currentUser.put("id", user.getId());
        currentUser.put("name", user.getName());
        session.setAttribute(CURRENT_USER_FACEBOOK, currentUser);
        RedirectView redirectView = new RedirectView(request.getHeader("referer"), true, true, true);
        return new ModelAndView(redirectView);
    }

    /**
     * Exchange Facebook code for Token,
     * save user in database,
     * add user in session,
     *
     * @param code Instagram access code
     * @return instagramUser
     * @throws InstagramException
     */
    @RequestMapping(value = "/callback/instagram", method = RequestMethod.GET)
    public ModelAndView callbackInstagram(
            @RequestParam("code") final String code,
            final HttpServletRequest request) throws InstagramException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JsonViewModule());
        InstagramService service = new InstagramAuthService().apiKey(CLIENT_ID_FOR_INSTAGRAM).apiSecret(CLIENT_ID_SECRET_FOR_INSTAGRAM).callback(REDIRECT_URI).build();
        Verifier verifier = new Verifier(code);
        Token accessToken = service.getAccessToken(verifier);
        Instagram instagram = new Instagram(accessToken);
        UserInfo userInfo = instagram.getCurrentUserInfo();
        InstagramUser instagramUser = new InstagramUser();
        instagramUser.setIdInstagram(String.valueOf(Long.parseLong(userInfo.getData().getId())));
        instagramUser.setFullName(userInfo.getData().getFullName());
        instagramUser.setNickName(userInfo.getData().getUsername());
        socialService.saveInstagramUser(instagramUser);
        request.getSession().setAttribute(CURRENT_USER_INSTAGRAM, instagramUser);
        RedirectView redirectView = new RedirectView(request.getHeader("referer"), true, true, true);
        return new ModelAndView(redirectView);
    }

    /**
     * Social login error handler
     *
     * @param errorReason reason
     * @param error       error
     * @param description description
     * @return reason + error + description
     */
    @GetMapping(value = "/callback", params = "error_reason")
    @ResponseBody
    public ResponseEntity<Map> loginError(@RequestParam("error_reason") final String errorReason,
                                          @RequestParam("error") final String error,
                                          @RequestParam("error_description") final String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(new HashMap() {{
            put("message", error + ". " + errorReason + " " + description);
        }}, headers, HttpStatus.I_AM_A_TEAPOT);
    }

    /**
     * Logout from application
     *
     * @param request request
     * @return redirect to main page
     */
    @GetMapping("/logout")
    public ModelAndView logoutFacebook(HttpServletRequest request) {
        if (request.getSession().getAttribute(CURRENT_USER_FACEBOOK) != null || request.getSession().getAttribute(CURRENT_USER_INSTAGRAM) != null) {
            request.getSession().invalidate();
        }
        RedirectView redirectView = new RedirectView(request.getHeader("referer"), true, true, true);
        return new ModelAndView(redirectView);
    }

}