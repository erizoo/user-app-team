package by.javateam.dao.Impl;

import by.javateam.model.FacebookUser;
import by.javateam.service.SocialService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class implements methods for access to MySQL database for FacebookUser.
 */
@Service
@SuppressWarnings("unchecked")
public class SocialDaoImpl implements SocialService {

    private final SessionFactory sessionFactory;

    @Autowired
    public SocialDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveUser(FacebookUser facebookUser) {
        sessionFactory.getCurrentSession().saveOrUpdate(facebookUser);
    }

}
