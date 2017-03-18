package by.javateam.service;

import by.javateam.model.FacebookUser;

/**
 * The interface defines methods of integration with social networks.
 */
public interface SocialService {

    /**
     * Save information about user Facebook.
     */
    void saveFacebookUser(FacebookUser facebookUser);
}
