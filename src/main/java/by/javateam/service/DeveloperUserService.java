package by.javateam.service;

import by.javateam.model.DeveloperUser;

/**
 * The interface determines the methods for work with DeveloperUser.
 */
public interface DeveloperUserService {

    DeveloperUser findByLogin(String login);

}
