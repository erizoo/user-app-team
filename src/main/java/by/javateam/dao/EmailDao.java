package by.javateam.dao;

import by.javateam.model.Email;

/**
 * The interface determines the basic methods of access to the database.
 */
public interface EmailDao {

    /**
     * Saves a email information into a storage.
     *
     * @param email email object to save
     */
    void save(Email email);
}
