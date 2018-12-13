package sashaVosu.firstWebApplication.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import sashaVosu.firstWebApplication.domain.ApplicationUser;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface UserRepo extends PagingAndSortingRepository<ApplicationUser, String> {

    ApplicationUser findOneByNickNameAndEmail(String nickname, String email);

    ApplicationUser findOneByNickName(String nickName);

    @Transactional
    void deleteOneById(Long userId);

    List<ApplicationUser> findAllByIdIn(Collection<Long> id);

    ApplicationUser findOneById(Long id);

    Page<ApplicationUser> findAllByActiveTrue(Pageable pageable);

    ApplicationUser findOneByIdAndActiveTrue(Long id);

    ApplicationUser findOneByNickNameAndActiveTrue(String nickName);

    Page<ApplicationUser> findAllByActiveTrueAndIdIn(Collection<Long> id, Pageable pageable);

    List<ApplicationUser> findAllByActiveTrueAndIdIn(Collection<Long> id);
}

