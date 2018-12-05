package sashaVosu.firstWebApplication.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.service.UserTweetLikesService;
import sashaVosu.firstWebApplication.utils.Utils;

import java.util.List;

@RestController
@RequestMapping("like")
public class LikeController {

    private final UserTweetLikesService userTweetLikesService;

    public LikeController(UserTweetLikesService userTweetLikesService) {
        this.userTweetLikesService = userTweetLikesService;
    }

    //put like to tweet. if successful returns true
    @PutMapping("{id}")
    public void putLike(@PathVariable("id") Long tweetId) {

        String nickName = Utils.getNickName();

        userTweetLikesService.like(tweetId, nickName);
    }

    //remove like from tweet
    @DeleteMapping("{id}")
    public void unlike(@PathVariable("id") Long tweetId) {

        String nickName = Utils.getNickName();

        userTweetLikesService.unlike(tweetId, nickName);
    }

    //return list of tweet what likes specific user
    @GetMapping
    public List<TweetModel> likesTweetList(Pageable pageable) {

        String nickName = Utils.getNickName();

        return userTweetLikesService.tweetWhatLike(nickName, pageable);

    }
}
