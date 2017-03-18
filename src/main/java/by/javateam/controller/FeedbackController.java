package by.javateam.controller;

import by.javateam.model.Email;
import by.javateam.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by valera.
 */
@Controller
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
            throw new ValidationException(preparingValidationMessage(result));
        }
        emailService.sendEmail(email);
        emailService.saveEmails(email);
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<Email> getAllFeedbacks() {
        return emailService.getAllInformationForEmails();
    }

    @ExceptionHandler(ValidationException.class)
    public HttpEntity<Map> validationError(final Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> info = new HashMap<>();
        info.put("message", ex.getMessage());
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(info, headers, HttpStatus.BAD_REQUEST);
    }

    private String preparingValidationMessage(final BindingResult result) {
        List<ObjectError> errors = result.getAllErrors();
        StringBuilder message = new StringBuilder("This field(s) is(are) wrong : ");
        for (ObjectError error: errors) {
            FieldError fe = (FieldError) error;
            message.append(fe.getField());
            message.append(" ");
        }
        return message.toString();
    }

}
