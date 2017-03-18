package by.javateam.service.Impl;

import by.javateam.dao.SocialDao;
import by.javateam.model.FacebookUser;
import by.javateam.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SocialServiceImpl implements SocialService{

    private final SocialDao socialDao;

    @Autowired
    public SocialServiceImpl(SocialDao socialDao) {
        this.socialDao = socialDao;
    }

    @Override
    public void saveUser(FacebookUser facebookUser) {
        socialDao.saveUser(facebookUser);
    }

}
