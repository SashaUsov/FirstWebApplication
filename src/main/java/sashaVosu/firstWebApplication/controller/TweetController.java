package sashaVosu.firstWebApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Error;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
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

    public TweetModel createTweet(@RequestBody CreateTweetModel model,
                                  @RequestHeader(value = "Authorization") String header)
    {
        String decode = new String(Base64.getDecoder().decode(header.split(" ")[1]), StandardCharsets.UTF_8);

        String nickName = decode.split(":")[0];

        String userPassword = decode.split(":")[1];

        return tweetService.tweetCreate(model, nickName, userPassword);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id,
                        @RequestHeader(value = "Authorization") String header)
    {
        String decode = new String(Base64.getDecoder().decode(header.split(" ")[1]), StandardCharsets.UTF_8);

        String nickName = decode.split(":")[0];

        String userPassword = decode.split(":")[1];

        tweetService.del(id, nickName, userPassword);
    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        e.printStackTrace();
        return new Error(e.getMessage());
    }
}
