package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.repo.TweetRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final TweetRepo tweetRepo;

    private final UserTweetLikesService tweetLikesService;

    public ProfileService(TweetRepo tweetRepo,
                          UserTweetLikesService tweetLikesService)
    {
        this.tweetRepo = tweetRepo;
        this.tweetLikesService = tweetLikesService;
    }

//get list of all tweets by specific user id. With data about likes
    public List<TweetModel> getListOfMyTweet(String nickName) {

        List<TweetModel> listOfTweetId = tweetRepo.findAllByCreator(nickName).stream()
                .map(Utils::convert)
                .collect(Collectors.toList());

        return listOfTweetId.stream()
                .map(a -> tweetLikesService.likeStatistic(a, nickName))
                .collect(Collectors.toList());
    }
}