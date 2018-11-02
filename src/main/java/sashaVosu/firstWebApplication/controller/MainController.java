package sashaVosu.firstWebApplication.controller;

import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.pojo.Pojo;

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
    public String createTweet(@RequestBody Pojo pojo) throws IndexOutOfBoundsException{
        if (pojo.getTweetText().length() > 1 && pojo.getTweetText().length() <= 140) {
            Tweet newOne = new Tweet();
            newOne.setText(pojo.getTweetText());
            newOne.setCreator(pojo.getCreator());
            newOne.setCreationData(LocalDateTime.now());
            tweetsList.add(newOne);

            return newOne.getText();
        }  else {
            throw new IndexOutOfBoundsException();
        }
    }
}
