package by.javateam.service.Impl;

import by.javateam.dao.EmailDao;
import by.javateam.model.Email;
import by.javateam.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private final EmailDao emailDao;

    @Autowired
    public EmailServiceImpl(EmailDao emailDao) {
        this.emailDao = emailDao;
    }

    @Override
    public void saveEmails(Email email) {
        emailDao.save(email);
    }

}
