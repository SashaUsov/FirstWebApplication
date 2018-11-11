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

//return list of all tweets
    public List<TweetModel> getTweetsList(String nickName) {

        List<Long> tweetIdList = tweetRepo.findAll().stream()
                .map(Tweet::getId)
                .collect(Collectors.toList());

        return tweetIdList.stream().map(a -> getOne(a, nickName)).collect(Collectors.toList());
    }

//create new tweet
    public TweetModel tweetCreate(CreateTweetModel tweetModel, String nickName){

            if (tweetModel.getTweetText().length() >= 1
                    && tweetModel.getTweetText().length() <= 140)
            {

                Tweet newTweet = TweetConverters.toEntity(tweetModel, nickName);

                return TweetConverters.toModel(tweetRepo.save(newTweet));

            } else {

                throw new NotAllowedLengthOfTextException("Not allowed length of tweet");
            }
    }

//delete one tweet by tweet id
    public void del(Long id, String nickName) {

            tweetRepo.deleteByIdAndCreator(id, nickName);

    }

//update tweet by tweet id
    public TweetModel update(CreateTweetModel model,
                             String currentPrincipalName, Long id)
    {
        Tweet toUpdate = tweetRepo.findOneByCreatorAndId(currentPrincipalName, id);

        toUpdate.setText(model.getTweetText());

        return TweetConverters.toModel(tweetRepo.save(toUpdate));
    }

//get one tweet by tweet id
    public TweetModel getOne(Long tweetId, String nickName) {

        TweetModel model = TweetConverters.toModel(tweetRepo.findOneById(tweetId));

        Long likeCount = userTweetLikesRepo.findAllByTweetId(tweetId).stream().count();

        model.setLikeCount(likeCount);

        boolean iLikeIt = tweetLikesService.whoLikeTweet(tweetId).stream()
                .anyMatch(a -> nickName.equals(a.getNickName()));

        model.setiLikeIt(iLikeIt);

        return model;
    }
}
