package by.javateam.dao.Impl;

import by.javateam.dao.EmailDao;
import by.javateam.model.Email;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class implements methods for access to MySQL database for Email.
 */
@Service
public class EmailDaoImpl implements EmailDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public EmailDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Email email) {
        sessionFactory.getCurrentSession().saveOrUpdate(email);
    }

}
