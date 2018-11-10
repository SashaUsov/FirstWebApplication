package sashaVosu.firstWebApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Error;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.CreateUserTweetLikesModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.service.GetNickNameUtils;
import sashaVosu.firstWebApplication.service.TweetService;
import sashaVosu.firstWebApplication.service.UserTweetLikesService;

import java.util.List;

@RestController
@RequestMapping("tweet")
public class TweetController {

    private final TweetService tweetService;

    private final UserTweetLikesService userTweetLikesService;

    public TweetController(TweetService tweetService, UserTweetLikesService userTweetLikesService) {
        this.tweetService = tweetService;
        this.userTweetLikesService = userTweetLikesService;
    }

//return list of all tweets
    @GetMapping("list")
    private List<TweetModel> listOfTweets() {
        return tweetService.getTweetsList();
    }

    @GetMapping("{id}")
    public TweetModel getOneTweet(@PathVariable("id") Long id){
        return tweetService.getOne(id);
    }

//create new tweet
    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetModel createTweet(@RequestBody CreateTweetModel model)
    {
        String currentPrincipalName = GetNickNameUtils.getNickName();

        return tweetService.tweetCreate(model, currentPrincipalName);
    }

//update one tweet by tweet id
    @PutMapping("{id}")
    public TweetModel updateTweet(@PathVariable("id") Long id,
                                  @RequestBody CreateTweetModel model)
    {
        String currentPrincipalName = GetNickNameUtils.getNickName();

        return tweetService.update(model, currentPrincipalName, id);
    }

//delete one tweet by tweet id
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id)
    {
        String currentPrincipalName = GetNickNameUtils.getNickName();

        tweetService.del(id, currentPrincipalName);
    }
//put like to tweet. if successful returns true
    @PutMapping("like")
    public boolean putLike(@RequestBody CreateUserTweetLikesModel userTweetLikes)
    {
        String nickName = GetNickNameUtils.getNickName();

        return userTweetLikesService.like(userTweetLikes, nickName);

    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        e.printStackTrace();
        return new Error(e.getMessage());
    }
}
