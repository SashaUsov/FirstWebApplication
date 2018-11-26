package sashaVosu.firstWebApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sashaVosu.firstWebApplication.domain.Error;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.fasades.TweetFacades;
import sashaVosu.firstWebApplication.service.Utils;
import sashaVosu.firstWebApplication.service.ReTweetService;
import sashaVosu.firstWebApplication.service.TweetService;
import sashaVosu.firstWebApplication.service.UserTweetLikesService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("tweet")
public class TweetController {

    private final TweetService tweetService;

    private final UserTweetLikesService userTweetLikesService;

    private final ReTweetService reTweetService;

    private final TweetFacades tweetFacades;

    public TweetController(TweetService tweetService,
                           UserTweetLikesService userTweetLikesService,
                           ReTweetService reTweetService, TweetFacades tweetFacades)
    {
        this.tweetService = tweetService;
        this.userTweetLikesService = userTweetLikesService;
        this.reTweetService = reTweetService;
        this.tweetFacades = tweetFacades;
    }

//return list of all tweets
    @GetMapping("list")
    private List<TweetModel> listOfTweets() {

        String nickName = Utils.getNickName();

        List<TweetModel> modelList = tweetService.getTweetsList();

        return tweetFacades.getTweetsList(nickName, modelList);
    }

    @GetMapping("{id}")
    public TweetModel getOneTweet(@PathVariable("id") Long id){

        String nickName = Utils.getNickName();

        TweetModel model = tweetService.getOne(id);

        return userTweetLikesService.likeStatistic(model, nickName);
    }

//return TweetModel to the user after creating new tweet
    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetModel createTweet(@RequestBody CreateTweetModel model
    ) {
        String nickName = Utils.getNickName();

        return tweetService.tweetCreate(model, nickName);
    }

//update one tweet by tweet id
    @PutMapping("{id}")
    public TweetModel updateTweet(@PathVariable("id") Long id,
                                  @RequestBody CreateTweetModel model)
    {
        String nickName = Utils.getNickName();

        return tweetService.update(model, nickName, id);
    }

//delete one tweet by tweet id
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id)
    {
        String nickName = Utils.getNickName();

        tweetService.del(id, nickName);
    }

//put like to tweet. if successful returns true
    @PutMapping("like/{id}")
    public void putLike(@PathVariable("id") Long tweetId) {

        String nickName = Utils.getNickName();

        userTweetLikesService.like(tweetId, nickName);
    }

//remove like from tweet
    @DeleteMapping("unlike/{id}")
    public void unlike(@PathVariable("id") Long tweetId) {

        String nickName = Utils.getNickName();

        userTweetLikesService.unlike(tweetId, nickName);
    }

//Repost tweet user to your page
    @PostMapping("retweet/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetModel createTweet(@RequestBody CreateTweetModel model,
                                  @PathVariable("id") Long tweetId
    ) {
        String nickName = Utils.getNickName();

        return reTweetService.reTweet(nickName, model, tweetId);
    }

//Get a list of tweets containing the original message
    @GetMapping("tweetShare/{id}")
    public List<TweetModel> tweetShare(@PathVariable("id") Long tweetId) {

        return reTweetService.listOfReTweets(tweetId);
    }

//Tweets shared by this user
    @GetMapping("iShared")
    public List<TweetModel> tweetsUserShared() {

        String nickName = Utils.getNickName();

        List<TweetModel> modelList = tweetService.getTweetsList();

        List<TweetModel> listWithLike = tweetFacades.getTweetsList(nickName, modelList);

        return tweetService.myReTweetList(listWithLike);
    }

    @PostMapping("add-pic")
    public String addTweetPic(@RequestParam("file") MultipartFile file) throws IOException {

        return tweetService.addTweetPic(file);
    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        e.printStackTrace();
        return new Error(e.getMessage());
    }
}
