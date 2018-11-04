package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.dto.CreateUserRequest;
import sashaVosu.firstWebApplication.exception.NotAllowLengthOfPasswordException;
import sashaVosu.firstWebApplication.exception.NotAllowedLengthOfNicknameException;
import sashaVosu.firstWebApplication.exception.NotValidEmailException;
import sashaVosu.firstWebApplication.exception.UserExistsException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> userList = new ArrayList<User>() {{
        User firstUser = new User();
        firstUser.setNickName("firstUser");
        firstUser.setEmail("sashavosu@gmail.com");
        firstUser.setFirstName("Sasha");
        firstUser.setLastName("Vosu");
        firstUser.setGender("male");
        firstUser.setAge("28");
        firstUser.setPassword("1234567");
        add(firstUser);
    }};

    public List<User> getUserList() {
        return userList;
    }

    public User userCreate(CreateUserRequest req) {

        if (!isUserExist(req.getNickName())
                && !isEmailExist(req.getEmail()))
        {

            if (req.getNickName().length() >= 3
                    && req.getNickName().length() <= 10)
            {

                User user = new User();
                user.setNickName(req.getNickName());

                if (req.getPassword().length() >= 6
                        && req.getPassword().length() <= 12)
                {
                    user.setPassword(req.getPassword());

                } else {

                    throw new NotAllowLengthOfPasswordException("Not allowed length of password");
                }
                user.setFirstName(req.getFirstName());
                user.setLastName(req.getLastName());

                if (!req.getEmail().isEmpty() && req.getEmail() != null)
                {
                    user.setEmail(req.getEmail());

                } else {
                    throw new NotValidEmailException("Not valid email");
                }

                user.setGender(req.getGender());
                user.setAge(req.getAge());
                userList.add(user);

                return user;

            } else {

                throw new NotAllowedLengthOfNicknameException("Not allowed length of nickname");
            }
        }

        throw new UserExistsException("User with this nickname or email already exists");
    }

    boolean isUserExist(String nickName) {

        for (User user : getUserList()){

            if (nickName.equals(user.getNickName())) return true;
        }

        return false;
    }

    private boolean isEmailExist(String email) {

        for (User user : getUserList()) {
            if (email.equals(user.getEmail())) return true;
        }

        return false;
    }

    boolean isPasswordMatches(String password) {

        for (User user : getUserList()) {
            if (password.equals(user.getPassword())) return true;
        }

        return false;
    }
}
