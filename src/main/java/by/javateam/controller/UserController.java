package by.javateam.controller;


import by.javateam.exception.ResourceNotFoundExceptionForGetUserId;
import by.javateam.model.User;
import by.javateam.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The controller determines methods for access to User service.
 */

@Controller
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController( final UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns list of all users.
     *
     * @return list of users
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public String getAllUsers(@RequestParam(value = "offset", required = false) Integer offset,
                              @RequestParam(value = "limit", required = false) Integer limit,
                              @RequestParam(value = "inc", required = false) String inc,
                              @RequestParam(value = "exc", required = false) String exc) throws JsonProcessingException {

        return userService.getAllWithParams(offset, limit, exc, inc);
    }

    /**
     * Update a user for id.
     *
     * @param id   identifier of a user
     * @param user model
     * @return json with find user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public User getStudent(@PathVariable("id") int id, @RequestBody User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        user.setModifiedTimestamp(localDateTime);
        user.setId(id);
        user.setCreatedTimestamp(userService.getCreatedDate(id));
        userService.update(user);
        return userService.getAllForId(id);
    }

    /**
     * Get a user for id.
     *
     * @param id identifier of a user
     * @return json with one user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getStudent(@PathVariable("id") int id) {
        userService.getAllForId(id);
        if (userService.getAllForId(id) == null) {
            throw new ResourceNotFoundExceptionForGetUserId();
        } else {
            return userService.getAllForId(id);
        }

    }

    /**
     * Deletes a user by identifier.
     *
     * @param userId identifier of a user to delete
     * @return refresh the page
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public List<User> deleteUser(@PathVariable("userId") int userId) {
        userService.getAllForId(userId);
        if (userService.getAllForId(userId) == null) {
            throw new ResourceNotFoundExceptionForGetUserId();
        } else
            userService.delete(userId);
        return userService.getAll();
    }

    /**
     * Save a new user.
     *
     * @return to page with all users
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public List<User> saveUser(@RequestBody User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        user.setCreatedTimestamp(localDateTime);
        userService.save(user);
        return userService.getAll();
    }

}
