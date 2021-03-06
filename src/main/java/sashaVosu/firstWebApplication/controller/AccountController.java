package sashaVosu.firstWebApplication.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.fasades.TweetFacades;
import sashaVosu.firstWebApplication.service.*;
import sashaVosu.firstWebApplication.utils.GetNickNameUtil;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    private final UserService userService;

    private final TweetService tweetService;

    private final TweetFacades tweetFacades;

    private final SubscriberService subscriberService;

    public AccountController(UserService userService,
                             TweetService tweetService,
                             TweetFacades tweetFacades,
                             SubscriberService subscriberService
    ) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.tweetFacades = tweetFacades;
        this.subscriberService = subscriberService;
    }

    //return list of tweet what create specific user
    @GetMapping
    public List<TweetModel> getMyTweetList(Pageable pageable) {
        String nickName = GetNickNameUtil.getNickName();

        List<TweetModel> myTweetList = tweetService.getListOfMyTweet(nickName, pageable);

        return tweetFacades.getTweetsList(nickName, myTweetList);
    }

    //add avatar image to user profile
    @PostMapping("img")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProfilePic(@RequestParam("file") MultipartFile file) throws IOException {

        String nickName = GetNickNameUtil.getNickName();

        userService.addProfilePic(nickName, file);

    }

    //delete user account and all user and like from user-tweet-like table
    //Deleted account cannot be recovered.
    @DeleteMapping
    public void deleteAccount() {

        String nickName = GetNickNameUtil.getNickName();

        userService.deleteProfile(nickName);
    }

    //Do I subscribe to this user
    @GetMapping("i-subscribe/{id}")
    public boolean iFollow(@PathVariable("id") Long channelId) {

        String nickName = GetNickNameUtil.getNickName();

        return subscriberService.iFollow(nickName, channelId);
    }

    //Is this user following me
    @GetMapping("subscribe-me/{id}")
    public boolean followMe(@PathVariable("id") Long channelId) {

        String nickName = GetNickNameUtil.getNickName();

        return subscriberService.followMe(nickName, channelId);
    }
}
