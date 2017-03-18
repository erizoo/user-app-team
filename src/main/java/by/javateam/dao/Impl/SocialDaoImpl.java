package by.javateam.dao.Impl;

import by.javateam.dao.SocialDao;
import by.javateam.model.FacebookUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class implements methods for access to MySQL database for FacebookUser.
 */
@Service
@SuppressWarnings("unchecked")
public class SocialDaoImpl implements SocialDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public SocialDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveFacebookUser(FacebookUser facebookUser) {
        sessionFactory.getCurrentSession().saveOrUpdate(facebookUser);
    }

}
