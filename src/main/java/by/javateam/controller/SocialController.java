package by.javateam.controller;

import com.valery.dao.facebookuser.FacebookUserDao;
import com.valery.model.facebookuser.FacebookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by valera.
 */
@Controller
public class SocialController {

    @Autowired
    Environment environment;
    @Autowired
    FacebookUserDao facebookUserDao;

    private static final String FACEBOOK = "facebook";
    private static final String FACEBOOK_ACCESS_TOKEN = "facebookAccessToken";
    private static final String CURRENT_USER_FACEBOOK = "currentUserFacebook";

    @Autowired
    private ConnectionFactoryRegistry connectionFactoryRegistry;

    @Autowired
    private OAuth2Parameters oAuth2Parameters;

    @GetMapping("/login/facebook")
    public ModelAndView loginFacebook() throws Exception {
        FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory) connectionFactoryRegistry
                .getConnectionFactory(FACEBOOK);
        OAuth2Operations oauthOperations = facebookConnectionFactory
                .getOAuthOperations();
        oAuth2Parameters.setState("user-app-test");
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(oAuth2Parameters);
        RedirectView redirectView = new RedirectView(authorizeUrl, true, true,
                true);

        return new ModelAndView(redirectView);
    }

    @GetMapping(value = "/callback/facebook")
    public String callbackFromFacebook(
            final @RequestParam("code") String code,
            final @RequestParam("state") String state,
            final HttpServletRequest request) throws Exception {

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
        FacebookUser facebookUser = new FacebookUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthday(),
                user.getGender());
        facebookUserDao.saveUser(facebookUser);
        Map<String, String> currentUser = new HashMap<String, String>();
        currentUser.put("id", user.getId());
        currentUser.put("name", user.getName());
        session.setAttribute(CURRENT_USER_FACEBOOK, currentUser);
        return "redirect: /";
    }

    @GetMapping(value = "/callback", params = "error_reason")
    @ResponseBody
    public ResponseEntity<Map> loginError(@RequestParam("error_reason") final String errorReason,
                                          @RequestParam("error") final String error,
                                          @RequestParam("error_description") final String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<Map>(new HashMap(){{put("message", error + ". " + errorReason + " " + description);}}, headers, HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping("/logout/facebook")
    public String logoutFacebook(HttpServletRequest request) {
        if (request.getSession().getAttribute(FACEBOOK_ACCESS_TOKEN) != null) {
            request.getSession().invalidate();
        }
        return "redirect: /";
    }
}
