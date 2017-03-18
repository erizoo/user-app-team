package by.javateam.controller;

import by.javateam.model.InstagramInfoUser;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.exceptions.InstagramException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

    private final static String CLIENT_ID = "0e37d7d0c3534a14a6d8448cdf5cef71";
    private final static String CLIENT_ID_SECRET = "629b16ba96a44b8db7ad34bbb54344aa";
    private final static String REDIRECT_URI = "http://snet2.herokuapp.com/callback";


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/login/instagram")
    public ModelAndView loginInstagram() {
        String uri = "https://api.instagram.com/oauth/authorize/?";
        String clientId = "client_id=";
        String redirectUri = "redirect_uri=";
        String code = "response_type=code";
        String authorizeUrl = uri +
                clientId + CLIENT_ID +
                "&" +
                redirectUri + REDIRECT_URI +
                "&" +
                code;
        RedirectView redirectView = new RedirectView(authorizeUrl, true, true,
                true);

        return new ModelAndView(redirectView);
    }

    @GetMapping(value = "/callback/instagram")
    @ResponseBody
    public InstagramInfoUser callbackInstagram(@RequestParam("code") String code) throws InstagramException {
        InstagramService service = new InstagramAuthService().apiKey(CLIENT_ID).apiSecret(CLIENT_ID_SECRET).callback(REDIRECT_URI).build();
        Verifier verifier = new Verifier(code);
        Token accessToken = service.getAccessToken(verifier);
        Instagram instagram = new Instagram(accessToken);
        UserInfo userInfo = instagram.getCurrentUserInfo();
        InstagramInfoUser ins = new InstagramInfoUser();
        ins.setId(Long.parseLong(userInfo.getData().getId()));
        ins.setFullName(userInfo.getData().getFullName());
        return ins;
    }

}