package sashaVosu.firstWebApplication.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.UserConverters;
import sashaVosu.firstWebApplication.domain.UserTweetLikes;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;
import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;
import sashaVosu.firstWebApplication.utils.TweetReTweetUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTweetLikesService {

    private final UserTweetLikesRepo userTweetLikesRepo;

    private final UserRepo userRepo;

    private final TweetRepo tweetRepo;

    public UserTweetLikesService(UserTweetLikesRepo userTweetLikesRepo,
                                 UserRepo userRepo,
                                 TweetRepo tweetRepo) {
        this.userTweetLikesRepo = userTweetLikesRepo;
        this.userRepo = userRepo;
        this.tweetRepo = tweetRepo;
    }

    //Add like to tweet
    public void like(Long tweetId, String nickName) {

        Long userId = getUserIdByNickName(nickName);

//        UserTweetLikes create = UserTweetLikes.builder()
//                .userId(userId)
//                .tweetId(tweetId)
//                .build();

        UserTweetLikes create = new UserTweetLikes();

        create.setUserId(userId);
        create.setTweetId(tweetId);
        create.setTimeWhenLiked(LocalDateTime.now());

        userTweetLikesRepo.save(create);
    }

    //remove like from tweet
    public void unlike(Long tweetId, String nickName) {

        Long userId = getUserIdByNickName(nickName);

        userTweetLikesRepo.deleteByUserIdAndTweetId(userId, tweetId);
    }

    //Return list of Tweet what user likes
    public List<TweetModel> tweetWhatLike(String nickName, Pageable pageable) {

        Long userIdByNickName = getUserIdByNickName(nickName);

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        List<Long> tweetIdList = getTweetIdList(userIdByNickName);

        List<TweetModel> tweetModelList = tweetRepo.findAllByPublishedTrueAndIdIn(tweetIdList,
                PageRequest.of(page, size, Sort.Direction.DESC, "creationData"))
                .stream()
                .map(TweetReTweetUtil::convert)
                .collect(Collectors.toList());

        return tweetModelList.stream()
                .map(a -> likeStatistic(a, nickName))
                .collect(Collectors.toList());
    }

    //set like count and user like status as to tweet
    public TweetModel likeStatistic(TweetModel model, String nickName) {

        int likeCount = userTweetLikesRepo.findAllByTweetId(model.getId()).size();

        boolean iLikeIt = whoLikeTweet(model.getId()).stream()
                .anyMatch(a -> nickName.equals(a.getNickName()));

        model.setLikeCount(likeCount);
        model.setILikeIt(iLikeIt);

        return model;

    }

    private Long getUserIdByNickName(String nickName) {
        return userRepo.findOneByNickNameAndActiveTrue(nickName).getId();
    }

    //Return list of tweet Id what specific user like
    private List<Long> getTweetIdList(Long userId) {
        return userTweetLikesRepo.findAllByUserId(userId).stream()
                .map(UserTweetLikes::getTweetId)
                .collect(Collectors.toList());
    }

    //return list of user who like tweet by tweet id
    private List<UserModel> whoLikeTweet(Long tweetId) {

        List<Long> userIdList = userTweetLikesRepo.findAllByTweetId(tweetId).stream()
                .map(UserTweetLikes::getUserId)
                .collect(Collectors.toList());

        return userRepo.findAllByIdIn(userIdList).stream()
                .map(UserConverters::toModel)
                .collect(Collectors.toList());
    }
}