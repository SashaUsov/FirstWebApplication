package sashaVosu.firstWebApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sashaVosu.firstWebApplication.domain.UserTweetLikes;

import javax.transaction.Transactional;
import java.util.List;

public interface UserTweetLikesRepo extends JpaRepository<UserTweetLikes, Long> {

    @Transactional
    void deleteByUserIdAndTweetId(Long userId, Long tweetId);

//Find all userTweet entity what specific user like
    List<UserTweetLikes> findAllByUserId(Long userId);

}
