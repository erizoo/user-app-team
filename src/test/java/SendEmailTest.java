import by.javateam.configuration.TestConfig;
import by.javateam.model.Email;
import by.javateam.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SendEmailTest {

    @Autowired
    private EmailService emailService;

    private Email email = new Email();

    @Before
    public void init() {
        email.setSubject("Thanks");
        email.setBody("Thank you, guys, you are awesome!");
        email.setCreatedTimestamp(String.valueOf(LocalDate.now()));
        email.setFrom("alexboiko1993@gmail.com");
    }

    @Test
    public void sendEmailTest() {
        emailService.sendEmail(email);
    }
}
