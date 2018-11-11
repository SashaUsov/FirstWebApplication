package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.repo.TweetRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final TweetRepo tweetRepo;

    private final TweetService tweetService;

    public ProfileService(TweetRepo tweetRepo, TweetService tweetService) {
        this.tweetRepo = tweetRepo;
        this.tweetService = tweetService;
    }

//get list of all tweets by specific user id. With data about likes
    public List<TweetModel> getListOfMyTweet(String nickName) {

        List<Long> listOfTweetId = tweetRepo.findAllByCreator(nickName).stream()
                .map(Tweet::getId)
                .collect(Collectors.toList());

        return listOfTweetId.stream()
                .map(a -> tweetService.getOne(a, nickName))
                .collect(Collectors.toList());
    }
}
