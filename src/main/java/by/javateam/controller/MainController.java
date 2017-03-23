package by.javateam.controller;

import by.javateam.exception.NoCurrentUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by valera.
 */
@Controller
public class MainController {

    private static final String CURRENT_USER_FACEBOOK = "currentUserFacebook";
    private static final String CURRENT_USER_INSTAGRAM = "currentUserInstagram";

    /**
     * Load start page.
     *
     * @return login jsp page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startPage(){
        return "index";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage(){
        return "error";
    }

    @GetMapping(value = "/api/current-user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Map getCurrentUser(final HttpServletRequest request) {
        HttpSession currentSession = request.getSession();
        Map currentUser;
        if (currentSession.getAttribute(CURRENT_USER_FACEBOOK) != null) {
            currentUser = (Map) currentSession.getAttribute(CURRENT_USER_FACEBOOK);
        } else if (currentSession.getAttribute(CURRENT_USER_INSTAGRAM) != null) {
            currentUser = (Map) currentSession.getAttribute(CURRENT_USER_INSTAGRAM);
        } else {
            throw new NoCurrentUserException();
        }
        return currentUser;
    }

    /**
     * Return main page with javascript code
     * @return main html page
     */
    @GetMapping("/swagger")
    public final String mainPage() {
        return "redirect: /static/index.html";
    }

    @ExceptionHandler(NoCurrentUserException.class)
    public final @ResponseBody ResponseEntity<Map> noCurrentUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, String> emptyUser = new HashMap<String, String>();
        emptyUser.put("id", "null");
        emptyUser.put("name", "null");
        return new ResponseEntity<Map>(emptyUser, headers, HttpStatus.UNAUTHORIZED);

    }
}
