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

        String nickName = GetNickNameUtils.getNickName();

        return tweetService.getTweetsList(nickName);
    }

    @GetMapping("{id}")
    public TweetModel getOneTweet(@PathVariable("id") Long id){

        String nickName = GetNickNameUtils.getNickName();

        return tweetService.getOne(id, nickName);
    }

//create new tweet
    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetModel createTweet(@RequestBody CreateTweetModel model)
    {
        String nickName = GetNickNameUtils.getNickName();

        return tweetService.tweetCreate(model, nickName);
    }

//update one tweet by tweet id
    @PutMapping("{id}")
    public TweetModel updateTweet(@PathVariable("id") Long id,
                                  @RequestBody CreateTweetModel model)
    {
        String nickName = GetNickNameUtils.getNickName();

        return tweetService.update(model, nickName, id);
    }

//delete one tweet by tweet id
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id)
    {
        String nickName = GetNickNameUtils.getNickName();

        tweetService.del(id, nickName);
    }

//put like to tweet. if successful returns true
    @PutMapping("like/{id}")
    public void putLike(@PathVariable("id") Long tweetId) {

        String nickName = GetNickNameUtils.getNickName();

        userTweetLikesService.like(tweetId, nickName);
    }

//remove like from tweet
    @DeleteMapping("unlike/{id}")
    public void unlike(@PathVariable("id") Long tweetId) {

        String nickName = GetNickNameUtils.getNickName();

        userTweetLikesService.unlike(tweetId, nickName);
    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        e.printStackTrace();
        return new Error(e.getMessage());
    }
}
