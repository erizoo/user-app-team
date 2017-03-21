package by.javateam.dao;

import by.javateam.model.DeveloperUser;

/**
 * The interface determines the basic methods of access to the database.
 */
public interface DeveloperUserDao {

    DeveloperUser findByLogin(String login);

}
