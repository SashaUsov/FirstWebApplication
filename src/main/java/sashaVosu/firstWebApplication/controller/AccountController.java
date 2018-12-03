package sashaVosu.firstWebApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.fasades.TweetFacades;
import sashaVosu.firstWebApplication.service.*;
import sashaVosu.firstWebApplication.utils.Utils;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    private final UserService userService;

    private final TweetService tweetService;

    private final TweetFacades tweetFacades;

    public AccountController(UserService userService,
                             TweetService tweetService,
                             TweetFacades tweetFacades
    ) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.tweetFacades = tweetFacades;
    }

    //return list of tweet what create specific user
    @GetMapping
    public List<TweetModel> getMyTweetList() {
        String nickName = Utils.getNickName();

        List<TweetModel> myTweetList = tweetService.getListOfMyTweet(nickName);

        return tweetFacades.getTweetsList(nickName, myTweetList);
    }

    //add avatar image to user profile
    @PostMapping("img")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProfilePic(@RequestParam("file") MultipartFile file) throws IOException {

        String nickName = Utils.getNickName();

        userService.addProfilePic(nickName, file);

    }

    //delete user account and all user and like from user-tweet-like table
//Deleted account cannot be recovered.
    @DeleteMapping
    public void deleteAccount() {

        String nickName = Utils.getNickName();

        userService.deleteProfile(nickName);
    }

}
