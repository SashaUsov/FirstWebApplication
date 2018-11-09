package sashaVosu.firstWebApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sashaVosu.firstWebApplication.domain.Tweet;

import javax.transaction.Transactional;

public interface TweetRepo extends JpaRepository<Tweet, Long> {

    @Transactional
    void deleteByIdAndCreator(Long id, String creator);

    Tweet findOneByCreatorAndId(String creator, Long id);

    Tweet findOneById(Long id);
}
