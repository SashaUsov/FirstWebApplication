package sashaVosu.firstWebApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.service.GetNickNameUtils;
import sashaVosu.firstWebApplication.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("listOfMyTweet")
    public List<TweetModel> getMyTweetList(){
        String currentPrincipalName = GetNickNameUtils.getNickName();

        return profileService.getListOfMyTweet(currentPrincipalName);
    }
}
