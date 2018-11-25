package sashaVosu.firstWebApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sashaVosu.firstWebApplication.domain.ApplicationUser;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface UserRepo extends JpaRepository<ApplicationUser, String> {

    ApplicationUser findOneByNickNameAndEmail(String nickname, String email);

    ApplicationUser findOneByNickName(String nickName);

    @Transactional
    void deleteOneById(Long userId);

    List<ApplicationUser> findAllByIdIn(Collection<Long> id);

    ApplicationUser findOneById(Long id);
}

