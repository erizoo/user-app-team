package by.javateam.dao;

import by.javateam.model.FacebookUser;
import by.javateam.model.InstagramUser;

/**
 * The interface determines the basic methods of access to the database.
 */
public interface SocialDao {

    /**
     * Save information about user Facebook into a storage.
     *
     * @param facebookUser object to save
     */
    void saveFacebookUser(FacebookUser facebookUser);

    /**
     * Save information about user Instagram into a storage.
     *
     * @param instagramUser object to save
     */
    void saveInstagramUser(InstagramUser instagramUser);
}
