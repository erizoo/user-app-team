import by.javateam.configuration.TestConfig;
import by.javateam.dao.EmailDao;
import by.javateam.model.Email;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class EmailSaveTest {

    @Autowired
    private EmailDao emailDao;

    private Email email = new Email();

    @Before
    public void init() {
        email.setSubject("Thanks");
        email.setBody("Thank you, guys, you are awesome!");
        email.setCreatedTimestamp(String.valueOf(LocalDate.now()));
        email.setFrom("romanalesenkov@gmail.com");
    }

    @Test
    @Transactional
    public void saveEmailTest() {
        emailDao.save(email);
        Assert.assertFalse(emailDao.loadAll().isEmpty());
    }
}
