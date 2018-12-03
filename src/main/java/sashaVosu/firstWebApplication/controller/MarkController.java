package sashaVosu.firstWebApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.service.TweetService;
import sashaVosu.firstWebApplication.service.UserService;
import sashaVosu.firstWebApplication.utils.Utils;

import java.util.List;

@RestController
@RequestMapping("mark")
public class MarkController {

    private final TweetService tweetService;

    private final UserService userService;

    public MarkController(TweetService tweetService,
                          UserService userService
    ) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    //get user list by mark in tweet
    @GetMapping("{id}")
    public List<UserModel> getMarkUserList(@PathVariable("id") Long tweetId) {

        return tweetService.getMarkUserList(tweetId);
    }

    //Returns a list of tweets in which users are tagged
    @GetMapping
    public List<TweetModel> getTweetListWhereIMark() {

        String nickName = Utils.getNickName();

        return userService.getTweetListWhereIMark(nickName);

    }
}
