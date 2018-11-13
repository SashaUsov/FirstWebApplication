package sashaVosu.firstWebApplication.converters;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;

public class UserConverters {

//method helper. convert user dto class to user entity
    public static User toEntity(CreateUserModel model) {

        final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

        User newUser = new User();
        newUser.setNickName(model.getNickName());
        newUser.setPassword(PASSWORD_ENCODER.encode(model.getPassword()));
        newUser.setFirstName(model.getFirstName());
        newUser.setLastName(model.getLastName());
        newUser.setEmail(model.getEmail());
        newUser.setGender(model.getGender());
        newUser.setAge(model.getAge());

        return newUser;
    }

//method helper. convert user entity to user dto model
    public static UserModel toModel(User user) {

        UserModel model = new UserModel();

        model.setNickName(user.getNickName());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setId(user.getId());
        model.setGender(user.getGender());
        model.setAge(user.getAge());
        model.setEmail(user.getEmail());
        model.setSubscribersCount(user.getSubscribers().size());
        model.setSubscriptionsCount(user.getSubscriptions().size());

        return model;
    }
}
