package sashaVosu.firstWebApplication.controller;

import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Tweet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("main")
public class MainController {

    private List<Tweet> tweetsList = new ArrayList<Tweet>(){{
        Tweet first = new Tweet();
        first.setText("First message in object");
        first.setCreationData(LocalDateTime.now());
        add(first);
    }};

    @GetMapping
    private List<Tweet> listOfTweets() {

        return tweetsList;
    }

    @PostMapping
    public String createTweet(@RequestBody String tweetText) throws IndexOutOfBoundsException{
        if (tweetText.length() > 1 && tweetText.length() <= 140) {
            Tweet newOne = new Tweet();
            newOne.setText(tweetText);
            newOne.setCreationData(LocalDateTime.now());
            tweetsList.add(newOne);
            return newOne.getText();
        }  else {
            throw new IndexOutOfBoundsException();
        }
    }

   /* private List<String> tweetsList = new ArrayList<String>(){{
        add("First application message");
        add("Second application message");
    }};

    @GetMapping
    private List<String> listOfTweets() {

        return tweetsList;
    }

    @PostMapping
    public String createTweet(@RequestBody String tweetText) throws IndexOutOfBoundsException{
        if (tweetText.length() > 1 && tweetText.length() <= 140) {
            tweetsList.add(tweetText);
            return tweetText;
        }  else {
            throw new IndexOutOfBoundsException();
        }
    }*/
}
