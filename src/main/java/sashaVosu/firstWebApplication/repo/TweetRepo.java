package sashaVosu.firstWebApplication.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import sashaVosu.firstWebApplication.domain.Tweet;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface TweetRepo extends PagingAndSortingRepository<Tweet, Long> {

    Tweet findOneByCreatorAndId(String creator, Long id);

    @Transactional
    void deleteAllByCreator(String nickName);

    @Transactional
    void deleteAllByFirstTweetIn(Collection<Tweet> firstTweet);

    List<Tweet> findAllByFirstTweet(Tweet tweet);

    Tweet findOneByIdAndPublishedTrue(Long id);

    Page<Tweet> findAllByPublishedTrue(Pageable pageable);

    Page<Tweet> findAllByCreatorAndPublishedTrue(String nickName, Pageable pageable);

    List<Tweet> findAllByPublishedTrueAndFirstTweetIn(Collection<Tweet> firstTweet);

    Page<Tweet> findAllByPublishedTrueAndIdIn(Collection<Long> id, Pageable pageable);

    List<Tweet> findAllByCreatorAndPublishedTrue(String nickName);
}