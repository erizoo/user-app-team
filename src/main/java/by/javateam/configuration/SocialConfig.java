package by.javateam.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;

import java.net.URISyntaxException;

/**
 * Created by valera.
 */
@Configuration
public class SocialConfig {

    private Environment environment;

    @Autowired
    public SocialConfig(final Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ConnectionFactoryRegistry connectionFactoryLocator() throws URISyntaxException {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        String facebookSecret = System.getenv("FACEBOOK_SECRET") != null ? System.getenv("FACEBOOK_SECRET") : environment.getRequiredProperty("facebook.clientSecret");

        registry.addConnectionFactory(new FacebookConnectionFactory(
                environment.getProperty("facebook.clientId"),
                facebookSecret));
        return registry;
    }

    @Bean
    public OAuth2Parameters oAuth2Parameters() {
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(environment.getProperty("facebook.callback"));
        parameters.setScope(environment.getProperty("facebook.scope"));
        return parameters;
    }
}
