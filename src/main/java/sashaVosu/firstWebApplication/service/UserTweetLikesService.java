package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.domain.UserTweetLikes;
import sashaVosu.firstWebApplication.domain.dto.CreateUserTweetLikesModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;
import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTweetLikesService {

    private final UserTweetLikesRepo userTweetRepo;

    private final UserRepo userRepo;

    private final TweetRepo tweetRepo;

    public UserTweetLikesService(UserTweetLikesRepo userTweetRepo,
                                 UserRepo userRepo, TweetRepo tweetRepo)
    {
        this.userTweetRepo = userTweetRepo;
        this.userRepo = userRepo;
        this.tweetRepo = tweetRepo;
    }

//Add like if boolean like=true (return true)
// and delete from userTweet table if like=false (return false)
public boolean like(CreateUserTweetLikesModel userTweetLikes, String nickName) {

    Long userId = userRepo.findOneByNickName(nickName).getId();

        if(userTweetLikes.isLike()) {

            UserTweetLikes create = new UserTweetLikes();

            create.setUserId(userId);
            create.setTweetId(userTweetLikes.getTweetId());
            create.setLike(userTweetLikes.isLike());

            userTweetRepo.save(create);

            return true;

        } else {

            userTweetRepo.deleteByUserIdAndTweetId(userId, userTweetLikes.getTweetId());

            return false;
        }
    }
//Return list of Tweet what user likes
    public List<TweetModel> tweetWhatLike (String nickName) {

        Long userId = userRepo.findOneByNickName(nickName).getId();

        List<Long> tweetIdList = getTweetIdList(userId);

        long likeCount = getTweetIdList(userId).size();

        return tweetRepo.findAllByIdIn(tweetIdList).stream()
                .map(TweetConverters::toModel)
                .collect(Collectors.toList());
    }

//Return list of tweet Id what specific user like
    private List<Long> getTweetIdList(Long userId) {
        return userTweetRepo.findAllByUserId(userId).stream()
                    .map(UserTweetLikes::getTweetId)
                    .collect(Collectors.toList());
    }
}