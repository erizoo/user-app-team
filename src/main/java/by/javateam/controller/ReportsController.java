package by.javateam.controller;

import by.javateam.model.Email;
import by.javateam.model.Report;
import by.javateam.model.User;
import by.javateam.service.EmailService;
import by.javateam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/reports")
public class ReportsController {

    private UserService userService;
    private EmailService emailService;

    @Autowired
    public ReportsController(final UserService userService, final EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    /**
     *
     * Return all available types of reports
     *
     * @return List of available reports
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Report>> getAllReports() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        // todo HARDCODE!!!
        List<Report> reports = new ArrayList<>();
        reports.add(new Report("Generate and download table with users.", "users", "Users"));
        reports.add(new Report("Generate and download table with feedback.", "feedback", "Feedback"));
        return new ResponseEntity<>(reports, responseHeaders, HttpStatus.OK);
    }

    /**
     *
     * Return PDF with list of all users
     *
     * @return PDF view
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView downloadPdfOfUsers() {
        List<User> listBooks = new ArrayList<>();
        for(User row : userService.getAll()){
            listBooks.add(new User(row.getFirstName(), row.getLastName()));
        }
        return new ModelAndView("pdfViewUsers", "listBooks", listBooks);
    }

    /**
     *
     * Return PDF with list of all emails
     *
     * @return PDF view
     */
    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public ModelAndView downloadPdfOfEmails() {
        Email email = new Email();
        List<Email> listEmails = new ArrayList<>();
        for(Email row : emailService.getAllInformationForEmails()){
            email.setBody(row.getBody());
            email.setFrom(row.getFrom());
            email.setSubject(row.getSubject());
            email.setCreatedTimestamp(row.getCreatedTimestamp());
            listEmails.add(new Email(row.getBody(), row.getFrom(), row.getSubject(), row.getCreatedTimestamp()));
        }
        return new ModelAndView("pdfViewEmails", "listEmails", listEmails);
    }

}