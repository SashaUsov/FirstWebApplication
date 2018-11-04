package sashaVosu.firstWebApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Error;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetRequest;
import sashaVosu.firstWebApplication.service.TweetService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("tweet")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    private List<Tweet> listOfTweets() {
        return tweetService.getTweetsList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public Tweet createTweet(@RequestBody CreateTweetRequest req,
                             @RequestHeader(value = "Authorization") String header)
    {
        String decode = new String(Base64.getDecoder().decode(header.split(" ")[1]), StandardCharsets.UTF_8);

        String nickName = decode.split(":")[0];

        String userPassword = decode.split(":")[1];

        return tweetService.tweetCreate(req, nickName, userPassword);
    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        return new Error(e.getMessage());
    }
}
