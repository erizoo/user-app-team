package by.javateam.service;

import by.javateam.model.FacebookUser;
import by.javateam.model.InstagramUser;

/**
 * The interface defines methods of integration with social networks.
 */
public interface SocialService {

    /**
     * Save information about user Facebook.
     *
     * @param facebookUser save object
     */
    void saveFacebookUser(FacebookUser facebookUser);

    /**
     * Save information about user Instagram.
     *
     * @param instagramUser save object
     */
    void saveInstagramUser(InstagramUser instagramUser);
}
