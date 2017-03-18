package by.javateam.controller;

import com.valery.dao.feedback.FeedbackDao;
import com.valery.dao.feedback.FeedbackDaoImplementation;
import com.valery.model.ExceptionJsonInfo;
import com.valery.model.feedback.Feedback;
import com.valery.service.email.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

/**
 * Created by valera on 2.3.17.
 */
@Controller
@RequestMapping("/api")
public class FeedbackController {

    private MailService mailService;
    private FeedbackDao feedbackDao;

    @Autowired
    public FeedbackController(final MailService mailService, final FeedbackDao feedbackDao) {
        this.mailService = mailService;
        this.feedbackDao = feedbackDao;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/feedback", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void sendFeedback(final @Valid @RequestBody Feedback feedback, final BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(preparingValidationMessage(result));
        }
        mailService.sendEmail(feedback);
        feedbackDao.createFeedback(feedback);
    }

    @GetMapping(value = "/feedback", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<Feedback> getAllFeedbacks() {
        return feedbackDao.getAllFeedbacks();
    }

    @ExceptionHandler(ValidationException.class)
    public HttpEntity<?> validationError(final Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        ExceptionJsonInfo info = new ExceptionJsonInfo();
        info.setMessage(ex.getMessage());
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<ExceptionJsonInfo>(info, headers, HttpStatus.NOT_FOUND);
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
