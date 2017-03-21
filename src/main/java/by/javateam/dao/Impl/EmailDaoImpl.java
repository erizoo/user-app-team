package by.javateam.dao.Impl;

import by.javateam.dao.EmailDao;
import by.javateam.model.Email;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The class implements methods for access to MySQL database for Email.
 */
@Service
@SuppressWarnings("unchecked")
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

    @Override
    public List<Email> loadAll() {
        return sessionFactory.getCurrentSession().createQuery("from Email").list();
    }

}
