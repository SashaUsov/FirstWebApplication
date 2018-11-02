package sashaVosu.firstWebApplication.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("main")
public class MainController {

    private List<String> tweetsList = new ArrayList<String>(){{
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
    }
}
