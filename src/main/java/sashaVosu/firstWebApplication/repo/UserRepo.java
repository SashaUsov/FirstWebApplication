package sashaVosu.firstWebApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sashaVosu.firstWebApplication.domain.User;

import javax.transaction.Transactional;

public interface UserRepo extends JpaRepository<User, String> {

    User findOneByNickNameAndEmail(String nickname, String email);

    User findOneByNickName(String nickName);

    @Transactional
    void deleteOneById(Long userId);
}

