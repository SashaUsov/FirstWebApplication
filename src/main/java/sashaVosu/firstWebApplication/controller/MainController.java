package sashaVosu.firstWebApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.exception.NotFoundException;
import sashaVosu.firstWebApplication.pojo.PojoTweet;
import sashaVosu.firstWebApplication.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("main")
public class MainController {

    @Autowired
    private UserService userService;

    private List<Tweet> tweetsList = new ArrayList<Tweet>() {{
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
    public String createTweet(@RequestBody PojoTweet pojoTweet) throws IndexOutOfBoundsException, NotFoundException {
        if (userService.getUser(pojoTweet.getCreator())) {

            if (pojoTweet.getTweetText().length() > 1 && pojoTweet.getTweetText().length() <= 140) {

                Tweet newOne = new Tweet();
                newOne.setText(pojoTweet.getTweetText());
                newOne.setCreator(pojoTweet.getCreator());
                newOne.setCreationData(LocalDateTime.now());
                tweetsList.add(newOne);

                return newOne.getText();

            } else {
                throw new IndexOutOfBoundsException("Not allowed length of nickname");
            }
        } else {
            throw  new NotFoundException("User with this nickname not found");
        }
    }
}
