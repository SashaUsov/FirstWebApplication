package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.exception.NotAllowedLengthOfTextException;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService {

    private final TweetRepo tweetRepo;

    private final UserTweetLikesRepo userTweetLikesRepo;

    private final UserTweetLikesService tweetLikesService;

    public TweetService(TweetRepo tweetRepo,
                        UserTweetLikesRepo userTweetLikesRepo,
                        UserTweetLikesService tweetLikesService)
    {
        this.tweetRepo = tweetRepo;
        this.userTweetLikesRepo = userTweetLikesRepo;
        this.tweetLikesService = tweetLikesService;
    }

//return list of all tweets with like count and user like status as to tweet
    public List<TweetModel> getTweetsList(String nickName) {

        List<Tweet> tweetList = tweetRepo.findAll();

        List<TweetModel> modelList = tweetList.stream()
                .map(Utils::convert).collect(Collectors.toList());

        return modelList.stream().map(a -> tweetLikesService.likeStatistic(a, nickName))
                .collect(Collectors.toList());
    }

//create new tweet
    public TweetModel tweetCreate(CreateTweetModel tweetModel, String nickName){

            if (tweetModel.getTweetText().length() >= 1
                    && tweetModel.getTweetText().length() <= 140)
            {

                Tweet newTweet = TweetConverters.toEntity(tweetModel, nickName);
                newTweet.setReTweet(false);

                tweetRepo.save(newTweet);

                return TweetConverters.toModel(newTweet);

            } else {

                throw new NotAllowedLengthOfTextException("Not allowed length of tweet");
            }
    }

//delete one tweet by tweet id from TWEET  and USER-LIKE-TWEET table
    public void del(Long id, String nickName) {

        Tweet tweet = tweetRepo.findOneById(id);

        if (tweet.isReTweet()) {
            userTweetLikesRepo.deleteAllByTweetId(id);

            Tweet firstTweet = tweet.getFirstTweet();
            firstTweet.getWhoReTweet().remove(tweet);
            tweetRepo.save(firstTweet);

            tweetRepo.deleteByIdAndCreator(id, nickName);

        } else {

            userTweetLikesRepo.deleteAllByTweetId(id);

            tweetRepo.deleteAllByFirstTweet(tweet);

            tweetRepo.deleteByIdAndCreator(id, nickName);
        }

    }

    public TweetModel getOne (Long id) {

        return Utils.convert(tweetRepo.findOneById(id));
    }

//update tweet by tweet id
    public TweetModel update(CreateTweetModel model,
                             String currentPrincipalName, Long id)
    {
        Tweet toUpdate = tweetRepo.findOneByCreatorAndId(currentPrincipalName, id);

        toUpdate.setText(model.getTweetText());

        return Utils.convert(tweetRepo.save(toUpdate));
    }
}