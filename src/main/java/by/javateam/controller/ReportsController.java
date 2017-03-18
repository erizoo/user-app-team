package by.javateam.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * Created by valera on 28.2.17.
 *
 * Controller work with reports api
 */
@Controller
@RequestMapping("/api/reports")
public class ReportsController {

    /**
     * User data access object
     */
    private UserDao userDao;
    /**
     * Feedback data access object
     */
    private FeedbackDao feedbackDao;

    /**
     * Constructor set user and feedback dao implementations
     * @param userDao User DAO implementation
     * @param feedbackDao Feedback DAO implementation
     */
    @Autowired
    public ReportsController(final UserDaoImplementation userDao, final FeedbackDaoImplementation feedbackDao) {
        this.userDao = userDao;
        this.feedbackDao = feedbackDao;
    }

    /**
     * Return all available types of reports
     * @return Map
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public final ResponseEntity<List<Report>> getAllReports() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        // todo HARDCODE!!!
        List<Report> reports = new ArrayList<Report>();
        reports.add(new Report("Generate and download table with users.", "users", "Users"));
        reports.add(new Report("Generate and download table with feedback.", "feedback", "Feedback"));
        return new ResponseEntity<List<Report>>(reports, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ModelAndView getUserList() throws Exception {
        List<?> users = userDao.getUsers();

        return new ModelAndView("userPdfView", "users", users);
    }

    @GetMapping("/feedback")
    public ModelAndView getFeedbackList() throws Exception {
        List<Feedback> feedbacks = feedbackDao.getAllFeedbacks();

        return new ModelAndView("feedbackPdfView", "feedbacks", feedbacks);
    }
}
