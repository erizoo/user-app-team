package by.javateam.service.Impl;

import by.javateam.dao.EmailDao;
import by.javateam.model.Email;
import by.javateam.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private final EmailDao emailDao;
    private JavaMailSender mailSender;
    private Environment environment;

    @Autowired
    public EmailServiceImpl(EmailDao emailDao) {
        this.emailDao = emailDao;
    }

    @Autowired
    public void setMailSender(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    @Override
    public void saveEmails(Email email) {
        emailDao.save(email);
    }

    @Override
    public boolean sendEmail(Email email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(System.getenv("RESPONDER_EMAIL") != null ? System.getenv("RESPONDER_EMAIL") : environment.getRequiredProperty("email.responser"));
        msg.setText(email.getBody() + "\r\n" + "sender: " + email.getFrom());
        msg.setSubject(email.getSubject());
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            return false;
        }
        return true;
    }

    @Override
    public List<Email> getAllInformationForEmails() {
        return emailDao.loadAll();
    }

}
