package by.javateam.controller;

import by.javateam.model.Email;
import by.javateam.model.User;
import by.javateam.service.EmailService;
import by.javateam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView downloadPdf() {
        User user = new User();
        java.util.List<User> listBooks = new ArrayList<User>();
        for(User row : userService.getNames()){
            user.setFirstName(row.getFirstName());
            user.setLastName(row.getLastName());
            listBooks.add(new User(row.getFirstName(), row.getLastName()));
        }
        return new ModelAndView("pdfView", "listBooks", listBooks);
    }


    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public ModelAndView downloadPdfOfEmails() {
        Email email = new Email();
        java.util.List<Email> listEmails = new ArrayList<Email>();
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