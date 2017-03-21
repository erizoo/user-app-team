package by.javateam.service.Impl;

import by.javateam.dao.DeveloperUserDao;
import by.javateam.model.DeveloperUser;
import by.javateam.service.DeveloperUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Erizo.
 */
@Service
@Transactional
public class DeveloperUserServiceImpl implements DeveloperUserService {

    private final DeveloperUserDao developerUserDao;

    @Autowired
    public DeveloperUserServiceImpl(DeveloperUserDao developerUserDao) {
        this.developerUserDao = developerUserDao;
    }

    @Override
    public DeveloperUser findByLogin(String login) {
        return developerUserDao.findByLogin(login);
    }

}
