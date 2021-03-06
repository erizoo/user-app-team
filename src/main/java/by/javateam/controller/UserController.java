package by.javateam.controller;


import by.javateam.exception.ResourceNotFoundExceptionForGetUserId;
import by.javateam.model.User;
import by.javateam.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * The controller determines methods for access to User service.
 */

@RestController
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
     * @return list of users in json
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getAllUsers(@RequestParam(value = "offset", required = false) Integer offset,
                              @RequestParam(value = "limit", required = false) Integer limit,
                              @RequestParam(value = "inc", required = false) String inc,
                              @RequestParam(value = "exc", required = false) String exc) throws JsonProcessingException {

        return userService.getAllUsersWithParams(offset, limit, exc, inc);
    }

    /**
     * Update a user for id.
     *
     * @param id   identifier of a user
     * @param user model
     * @return updated user in json
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getUsers(@PathVariable("id") int id, @RequestBody User user) {
        user.setId(id);
        return userService.update(user);
    }

    /**
     * Get a user for id.
     *
     * @param id identifier of a user
     * @return user in json
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getUser(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundExceptionForGetUserId();
        } else {
            return user;
        }
    }

    /**
     * Deletes a user by identifier.
     *
     * @param userId identifier of a user to delete
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("userId") int userId) {
        if (userService.getUserById(userId) == null) {
            throw new ResourceNotFoundExceptionForGetUserId();
        } else {
            userService.delete(userId);
        }
    }

    /**
     * Save a new user.
     *
     * @return to page with all users
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User saveUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    /**
     *
     * Validation exception handler
     *
     * @param ex hold wrong fields and messages
     * @return response status with messages
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String prepareValidationErrorMessage(ConstraintViolationException ex) {
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation violation: ex.getConstraintViolations()) {
            builder.append("Field ").
                    append(violation.getPropertyPath()).
                    append(" ").
                    append(violation.getMessage()).
                    append("; ");
        }
        return builder.toString();
    }

    /**
     *
     * User not found by ID exception handler
     *
     * @return exception message
     */
    @ExceptionHandler(ResourceNotFoundExceptionForGetUserId.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String prepareNoUserMessage() {
        return "No such user.";
    }

}
