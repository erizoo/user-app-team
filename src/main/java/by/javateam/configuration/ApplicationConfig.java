package by.javateam.configuration;

import by.javateam.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Configurer for database and mail.
 *
 * Created by valera.
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:hibernate.properties",
                         "classpath:data-base.properties",
                         "classpath:email.properties"})
@ComponentScan(basePackageClasses = {User.class})
public class ApplicationConfig {

    private Environment environment;

    /**
     * Environment for database and mail configuration files
     * @param environment
     */
    @Autowired
    public ApplicationConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * @return deploy heroku
     * @throws URISyntaxException
     */
    @Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        String path;
        String username;
        String password;

        if (System.getenv("DATABASE_PATH") == null) {
            path = environment.getProperty("localhost.path");
            username = environment.getProperty("localhost.username");
            password = environment.getProperty("localhost.password");
        } else {
            path = System.getenv("DATABASE_PATH");
            username = System.getenv("DATABASE_USER");
            password = System.getenv("DATABASE_PASSWORD");
        }
        String dbUrl = "jdbc:mysql://" + path;

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws SQLException, URISyntaxException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("by.javateam.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    /**
     * Settings for email. User Gmail account
     * @return configured email sender
     */
    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        final int gmailPort = 587;

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(gmailPort);
        String userEmail = System.getenv("MAIL_USER") != null ? System.getenv("MAIL_USER") : environment.getRequiredProperty("email.userEmail");
        String password = System.getenv("MAIL_PASSWORD") != null ? System.getenv("MAIL_PASSWORD") : environment.getProperty("email.password");

        mailSender.setUsername(userEmail);
        mailSender.setPassword(password);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
}
