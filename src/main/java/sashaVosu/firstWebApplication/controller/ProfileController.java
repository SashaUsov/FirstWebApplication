package sashaVosu.firstWebApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.service.GetNickNameUtils;
import sashaVosu.firstWebApplication.service.ProfileService;
import sashaVosu.firstWebApplication.service.UserTweetLikesService;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final ProfileService profileService;

    private final UserTweetLikesService userTweetLikesService;

    public ProfileController(ProfileService profileService,
                             UserTweetLikesService userTweetLikesService)
    {
        this.profileService = profileService;
        this.userTweetLikesService = userTweetLikesService;
    }

//return list of tweet what create specific user
    @GetMapping("listOfMyTweet")
    public List<TweetModel> getMyTweetList(){
        String currentPrincipalName = GetNickNameUtils.getNickName();

        return profileService.getListOfMyTweet(currentPrincipalName);
    }

//return list of tweet what likes specific user
    @GetMapping("likesTweet")
    public List<TweetModel> likesTweetList () {

        String nickName = GetNickNameUtils.getNickName();

        return userTweetLikesService.tweetWhatLike(nickName);

    }
}
