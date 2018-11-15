package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.ReTweetConverters;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.exception.NotAllowedLengthOfTextException;
import sashaVosu.firstWebApplication.repo.TweetRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReTweetService {

    private final TweetRepo tweetRepo;

    private final  ProfileService profileService;

    public ReTweetService(TweetRepo tweetRepo,
                          ProfileService profileService)
    {
        this.tweetRepo = tweetRepo;
        this.profileService = profileService;
    }

//Repost tweet user to your page
    public TweetModel reTweet(String nickName,
                              CreateTweetModel tweetModel,
                              Long tweetId) {

        Tweet tweetFromDb = tweetRepo.findOneById(tweetId);

        Tweet reTweet = TweetConverters.toEntity(tweetModel, nickName);


        if (tweetModel.getTweetText().length() >= 1
                && tweetModel.getTweetText().length() <= 140) {

            if (!tweetFromDb.isReTweet()) {

                reTweet.setReTweet(true);
                reTweet.setFirstTweet(tweetFromDb);

                tweetFromDb.getWhoReTweet().add(reTweet);
                tweetRepo.save(tweetFromDb);
                tweetRepo.save(reTweet);

                return ReTweetConverters.toModel(reTweet);
            } else {

                Tweet firstTweet = tweetFromDb.getFirstTweet();

                reTweet.setReTweet(true);
                reTweet.setFirstTweet(firstTweet);

                firstTweet.getWhoReTweet().add(reTweet);
                tweetRepo.save(firstTweet);

                return ReTweetConverters.toModel(tweetRepo.save(reTweet));
            }

        } else {

            throw new NotAllowedLengthOfTextException("Not allowed length of tweet");
        }
    }

//Get a list of tweets containing the original message
    public List<TweetModel> listOfReTweets(Long tweetId) {

        Tweet tweetFromDb = tweetRepo.findOneById(tweetId);

        return tweetFromDb.getWhoReTweet().stream()
                .map(ReTweetConverters::toModel)
                .collect(Collectors.toList());
    }

//Tweets shared by this user
    public List<TweetModel> myReTweetList(String nickName) {

        List<TweetModel> myTweets = profileService.getListOfMyTweet(nickName);

        return myTweets.stream().filter(TweetModel::isReTweet).collect(Collectors.toList());
    }
}
