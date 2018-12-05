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

    Tweet findOneByIdAndPublished(Long id, boolean published);

    Page<Tweet> findAllByPublished(boolean published, Pageable pageable);

    Page<Tweet> findAllByCreatorAndPublished(String nickName, boolean published, Pageable pageable);

    List<Tweet> findAllByPublishedAndFirstTweetIn(boolean published, Collection<Tweet> firstTweet);

    Page<Tweet> findAllByPublishedAndIdIn(boolean published, Collection<Long> id, Pageable pageable);

    List<Tweet> findAllByCreatorAndPublished(String nickName, boolean published);
}