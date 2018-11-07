package sashaVosu.firstWebApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Error;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.service.GetNickName;
import sashaVosu.firstWebApplication.service.TweetService;

import java.util.List;

@RestController
@RequestMapping("tweet")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("list")
    private List<TweetModel> listOfTweets() {
        return tweetService.getTweetsList();
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetModel createTweet(@RequestBody CreateTweetModel model)
    {
        String currentPrincipalName = GetNickName.getNickName();

        return tweetService.tweetCreate(model, currentPrincipalName);
    }

    @PutMapping("{id}")
    public TweetModel updateTweet(@PathVariable("id") Long id,
                                  @RequestBody CreateTweetModel model)
    {
        String currentPrincipalName = GetNickName.getNickName();

        return tweetService.update(model, currentPrincipalName, id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id)
    {
        String currentPrincipalName = GetNickName.getNickName();

        tweetService.del(id, currentPrincipalName);
    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        e.printStackTrace();
        return new Error(e.getMessage());
    }
}
