package sashaVosu.firstWebApplication.fasades;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.service.UserTweetLikesService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetFacades {

    private final UserTweetLikesService tweetLikesService;

    public TweetFacades(UserTweetLikesService tweetLikesService) {
        this.tweetLikesService = tweetLikesService;
    }

    //return list of all tweets with like statistic
    public List<TweetModel> getTweetsList(String nickName,
                                          List<TweetModel> modelList
    ) {

        return modelList.stream().map(a -> tweetLikesService.likeStatistic(a, nickName))
                .collect(Collectors.toList());
    }


}
