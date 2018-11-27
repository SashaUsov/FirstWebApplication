package sashaVosu.firstWebApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sashaVosu.firstWebApplication.domain.HashTag;

public interface HashTagRepo extends JpaRepository<HashTag, Long> {

    HashTag findByTag(String tag);
}