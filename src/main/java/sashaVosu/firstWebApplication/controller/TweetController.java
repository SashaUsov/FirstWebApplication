package sashaVosu.firstWebApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Error;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetRequest;
import sashaVosu.firstWebApplication.service.TweetService;
import sashaVosu.firstWebApplication.service.UserService;

import java.util.List;

@RestController
@RequestMapping("tweet")
public class TweetController {

    private final UserService userService;

    private final TweetService tweetService;

    public TweetController(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @GetMapping
    private List<Tweet> listOfTweets() {
        return tweetService.getTweetsList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet createTweet(@RequestBody CreateTweetRequest req) {

        return tweetService.tweetCreate(req);
    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        return new Error(e.getMessage());
    }
}
