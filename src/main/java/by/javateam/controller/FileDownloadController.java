package by.javateam.controller;

import by.boiko.snet.model.Email;
import by.boiko.snet.model.User;
import by.boiko.snet.service.EmailService;
import by.boiko.snet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class FileDownloadController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "reports/users", method = RequestMethod.GET)
    public ModelAndView downloadPdf() {
        User user = new User();
        java.util.List<User> listBooks = new ArrayList<User>();
        for(User row : userService.getNames()){
            user.setFirstName(row.getFirstName());
            user.setLastName(row.getLastName());
            String userSting = row.getFirstName() + " " + row.getLastName();
            listBooks.add(new User(row.getFirstName(), row.getLastName()));
        }
        return new ModelAndView("pdfView", "listBooks", listBooks);
    }


    @RequestMapping(value = "/reports/feedback", method = RequestMethod.GET)
    public ModelAndView downloadPdfOfEmails() {
        Email email = new Email();
        java.util.List<Email> listEmails = new ArrayList<Email>();
        for(Email row : emailService.getAll()){
            email.setBody(row.getBody());
            email.setFrom(row.getFrom());
            email.setSubject(row.getSubject());
            email.setCreatedTimestamp(row.getCreatedTimestamp());
            listEmails.add(new Email(row.getBody(), row.getFrom(), row.getSubject(), row.getCreatedTimestamp()));
        }
            return new ModelAndView("pdfViewEmails", "listEmails", listEmails);
    }

}