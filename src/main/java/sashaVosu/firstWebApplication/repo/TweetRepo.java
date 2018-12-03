package sashaVosu.firstWebApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sashaVosu.firstWebApplication.domain.Tweet;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface TweetRepo extends JpaRepository<Tweet, Long> {

    Tweet findOneByCreatorAndId(String creator, Long id);

    @Transactional
    void deleteAllByCreator(String nickName);

    @Transactional
    void deleteAllByFirstTweetIn(Collection<Tweet> firstTweet);

    List<Tweet> findAllByFirstTweet(Tweet tweet);

    Tweet findOneByIdAndPublished(Long id, boolean published);

    List<Tweet> findAllByPublished(boolean published);

    List<Tweet> findAllByCreatorAndPublished(String nickName, boolean published);

    List<Tweet> findAllByPublishedAndFirstTweetIn(boolean published, Collection<Tweet> firstTweet);

    List<Tweet> findAllByPublishedAndIdIn(boolean published, Collection<Long> id);
}