package by.javateam.service;


import by.javateam.model.Email;

import java.util.List;

/**
 * The interface determines the methods for work with Email.
 */

public interface EmailService {

    /**
     * Save all e-mails.
     */
    void saveEmails(Email email);

    /**
     * Send e-email.
     *
     * @param email object model
     * @return send status
     */
    boolean sendEmail(final Email email);

    /**
     * Get list of all emails.
     *
     * @return list of emails
     */
    List<Email> getAllInformationForEmails();
}
