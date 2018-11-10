package sashaVosu.firstWebApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sashaVosu.firstWebApplication.domain.Tweet;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface TweetRepo extends JpaRepository<Tweet, Long> {

    @Transactional
    void deleteByIdAndCreator(Long id, String creator);

    Tweet findOneByCreatorAndId(String creator, Long id);

    Tweet findOneById(Long id);

    List<Tweet> findAllByCreator(String currentPrincipalName);

    List<Tweet> findAllByIdIn(Collection<Long> tweetIdList);
}
