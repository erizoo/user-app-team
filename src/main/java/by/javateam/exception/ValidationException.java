package by.javateam.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by valera.
 */
public class ValidationException extends RuntimeException {

    private List<ObjectError> errors;

    public ValidationException(final List<ObjectError> errors) {
        super();
        this.errors = errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}
