package sashaVosu.firstWebApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.dto.UserModel;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {

    User findOneByNickNameAndEmail(String nickname, String email);

    User findOneByNickName(String nickName);

    @Transactional
    void deleteOneById(Long userId);

    List<User> findAllByIdIn(Collection<Long> id);

    User findOneById(Long id);
}

