package by.javateam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with this id not found")
public class ResourceNotFoundExceptionForGetUserId extends RuntimeException {

}