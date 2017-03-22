package by.javateam.controller;

import by.javateam.exception.ValidationException;
import by.javateam.model.Email;
import by.javateam.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by valera.
 */
@RestController
@RequestMapping("/api")
public class FeedbackController {


    private EmailService emailService;

    @Autowired
    public FeedbackController(final EmailService emailService) {
        this.emailService = emailService;
    }



    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/feedback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void sendFeedback(final @Valid @RequestBody Email email, final BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors());
        }
        emailService.sendEmail(email);
        emailService.saveEmails(email);
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Email> getAllFeedbacks() {
        return emailService.getAllInformationForEmails();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String validationError(final ValidationException ex) {
        StringBuilder builder = new StringBuilder();
        for (ObjectError error: ex.getErrors()) {
            FieldError fieldError = (FieldError) error;
            builder.append("Field ")
                    .append(fieldError.getField())
                    .append(" ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }
        return builder.toString();
    }

}
