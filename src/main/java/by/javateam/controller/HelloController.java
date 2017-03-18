package by.javateam.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;

@Controller

public class HelloController {

    private final static String CONSUMER_KEY = "PT6uezLMteHqTsq2VO3qio3cT";
    private final static String CONSUMER_KEY_SECRET = "6TbfhDYTprrYUh5RIuL9Mml4hcbDQk9sVOWxpkZsS3SNWWkJnb";
    private final static String ACCESS_TOKEN = "2482477239-pmGBbS1CwltUJhcNuOvXDpRTB1zUvpHzvJ7Aaga";
    private final static String ACCESS_TOKEN_SECRET = "2482477239-pmGBbS1CwltUJhcNuOvXDpRTB1zUvpHzvJ7Aaga";

    @RequestMapping("/connect/twitter")
    public String testPostingToTwitter() throws TwitterException, IOException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = new TwitterFactory().getInstance();
        System.out.println("key:" + twitter.getConfiguration().getOAuthConsumerKey());
        System.out.println("secret: " + twitter.getConfiguration().getOAuthConsumerSecret());
        return "redirect:https://api.instagram.com";
    }
}
