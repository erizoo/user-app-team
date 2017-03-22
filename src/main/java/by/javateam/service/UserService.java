package by.javateam.service;

import by.javateam.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * The interface determines the methods for work with User.
 */
public interface UserService {

    /**
     * Loads list of all users with params.
     *
     * @param offset the displacement of the first record
     * @param limit  the maximum number of entries
     * @return list users
     */
    List<User> getAll(int offset, int limit);

    /**
     * Loads a user by id.
     *
     * @param id identifier of a user
     * @return a user
     */
    User getUserById(int id);

    /**
     * Deletes a user by id.
     *
     * @param id identifier of a user
     */
    void delete(int id);

    /**
     * Saves a user.
     *
     * @param user user object to save
     */
    void save(User user);

    /**
     * Update a user.
     *
     * @param user user object to update
     */
    User update(User user);

    /**
     * Get list of all users.
     *
     * @return list users
     */
    List<User> getAll();

    /**
     * Get list the names and surnames of users.
     *
     * @return list names and surnames users
     */
    List<User> getNames();

    /**
     * Get creation date a user.
     *
     * @param id identifier of a user
     * @return creation date a user
     */
    String getCreatedDate(int id);

    /**
     * Get a recording with params.
     *
     * @param offset params
     * @param limit  params
     * @param exc    params
     * @param inc    params
     * @return list users
     */
    String getAllUsersWithParams(Integer offset, Integer limit, String exc, String inc) throws JsonProcessingException;

    Number countAll();
}