package sashaVosu.firstWebApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sashaVosu.firstWebApplication.domain.User;

public interface UserRepo extends JpaRepository<User, String> {

    User findOneByNickNameAndEmail(String nickname, String email);

    User findOneByNickName(String nickName);
}
