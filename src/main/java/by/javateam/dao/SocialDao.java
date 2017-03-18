package by.javateam.dao;

import by.javateam.model.FacebookUser;

/**
 * The interface determines the basic methods of access to the database.
 */
public interface SocialDao {

    /**
     * Save information about user Facebook into a storage.
     *
     * @param facebookUser object to save
     */
    void saveUser(FacebookUser facebookUser);
}
