package sashaVosu.firstWebApplication.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import sashaVosu.firstWebApplication.domain.ApplicationUser;
import sashaVosu.firstWebApplication.exception.UserNotFoundException;
import sashaVosu.firstWebApplication.repo.UserRepo;

@Component
public class DetailsService implements UserDetailsService {

    private final UserRepo users;

    public DetailsService(UserRepo users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {

        ApplicationUser user = users.findOneByNickName(username);
        if (user == null) {
            throw new UserNotFoundException(username + " was not found");
        }
        return new User(
                user.getNickName(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList()
        );
    }
}
