package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.UserConverters;
import sashaVosu.firstWebApplication.domain.UserTweetLikes;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;
import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;
import sashaVosu.firstWebApplication.utils.Utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTweetLikesService {

    private final UserTweetLikesRepo userTweetRepo;

    private final UserRepo userRepo;

    private final TweetRepo tweetRepo;

    public UserTweetLikesService(UserTweetLikesRepo userTweetRepo,
                                 UserRepo userRepo,
                                 TweetRepo tweetRepo)
    {
        this.userTweetRepo = userTweetRepo;
        this.userRepo = userRepo;
        this.tweetRepo = tweetRepo;
    }

//Add like to tweet
public void like(Long tweetId, String nickName) {

    Long userId = getUserIdByNickName(nickName);

            UserTweetLikes create = new UserTweetLikes();

            create.setUserId(userId);
            create.setTweetId(tweetId);
            create.setTimeWhenLiked(LocalDateTime.now());

            userTweetRepo.save(create);
    }

//remove like from tweet
    public void unlike(Long tweetId, String nickName) {

        Long userId = getUserIdByNickName(nickName);

        userTweetRepo.deleteByUserIdAndTweetId(userId, tweetId);
    }

//Return list of Tweet what user likes
    public List<TweetModel> tweetWhatLike (String nickName) {

        Long userIdByNickName = getUserIdByNickName(nickName);

        List<Long> tweetIdList = getTweetIdList(userIdByNickName);

        List<TweetModel> tweetModelList = tweetRepo.findAllByIdIn(tweetIdList).stream()
                .map(Utils::convert)
                .collect(Collectors.toList());

        return tweetModelList.stream().map(a -> likeStatistic(a, nickName))
                .collect(Collectors.toList());
    }

//set like count and user like status as to tweet
    public TweetModel likeStatistic(TweetModel model, String nickName) {

        Long likeCount = (long) userTweetRepo.findAllByTweetId(model.getId()).size();

        model.setLikeCount(likeCount);

        boolean iLikeIt = whoLikeTweet(model.getId()).stream()
                    .anyMatch(a -> nickName.equals(a.getNickName()));

        model.setILikeIt(iLikeIt);

        return model;

    }

    private Long getUserIdByNickName(String nickName) {
        return userRepo.findOneByNickName(nickName).getId();
    }

//Return list of tweet Id what specific user like
    private List<Long> getTweetIdList(Long userId) {
        return userTweetRepo.findAllByUserId(userId).stream()
                .map(UserTweetLikes::getTweetId)
                .collect(Collectors.toList());
    }

//return list of user who like tweet by tweet id
    private List<UserModel> whoLikeTweet(Long tweetId) {

        List<Long> userIdList =  userTweetRepo.findAllByTweetId(tweetId).stream()
                .map(UserTweetLikes::getUserId)
                .collect(Collectors.toList());

        return userRepo.findAllByIdIn(userIdList).stream()
                .map(UserConverters::toModel)
                .collect(Collectors.toList());
    }
}