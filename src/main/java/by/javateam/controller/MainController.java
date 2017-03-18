package by.javateam.controller;

import com.valery.exception.NoCurrentUserException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping(value = "/current-user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    List<Map<String, String>> getCurrentUser(final HttpServletRequest request) {
        HttpSession currentSession = request.getSession();
        List<Map<String, String>> currentUser = new ArrayList<Map<String, String>>();
        if (currentSession.getAttribute(CURRENT_USER_FACEBOOK) != null) {
            currentUser.add((Map<String, String>) currentSession.getAttribute(CURRENT_USER_FACEBOOK));
        }
        if (currentSession.getAttribute(CURRENT_USER_INSTAGRAM) != null) {
            currentUser.add((Map<String, String>) currentSession.getAttribute(CURRENT_USER_INSTAGRAM));
        }
        if (currentUser.isEmpty()) {
            throw new NoCurrentUserException();
        }
        return currentUser;
    }

    /**
     * Index jsp
     * @return string with jsp name
     */
    @GetMapping("/")
    public final String index() {
        return "index";
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
