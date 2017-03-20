package by.javateam.dao.Impl;

import by.javateam.dao.DeveloperUserDao;
import by.javateam.model.DeveloperUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class implements methods for access to MySQL database for DeveloperUser.
 */
@Service
public class DeveloperUserDaoImpl implements DeveloperUserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public DeveloperUserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public DeveloperUser findByLogin(String login) {
        return (DeveloperUser) sessionFactory.getCurrentSession().createQuery("select u from DeveloperUser u where login = :login").setParameter("login", login).uniqueResult();
    }

}
