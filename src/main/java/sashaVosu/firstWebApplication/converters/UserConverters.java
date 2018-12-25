package sashaVosu.firstWebApplication.converters;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sashaVosu.firstWebApplication.domain.ApplicationUser;
import sashaVosu.firstWebApplication.domain.Role;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;

import java.time.LocalDateTime;
import java.util.Collections;

public class UserConverters {

    //method helper. convert user dto class to user entity
    public static ApplicationUser toEntity(CreateUserModel model) {

        final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

        ApplicationUser newUser = new ApplicationUser();

        newUser.setNickName(model.getNickName());
        newUser.setPassword(PASSWORD_ENCODER.encode(model.getPassword()));
        newUser.setFirstName(model.getFirstName());
        newUser.setLastName(model.getLastName());
        newUser.setEmail(model.getEmail().toLowerCase());
        newUser.setGender(model.getGender());
        newUser.setAge(model.getAge());
        newUser.setActive(true);
        newUser.setRegistration(LocalDateTime.now());
        newUser.setRoles(Collections.singleton(Role.USER));

        return newUser;
    }

    //method helper. convert user entity to user dto model
    public static UserModel toModel(ApplicationUser user) {

        UserModel.UserBuilder model = UserModel.newBuilder()
                .setNickName(user.getNickName())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setId(user.getId())
                .setGender(user.getGender())
                .setAge(user.getAge())
                .setEmail(user.getEmail());

        if (user.getFileName() != null) {
            model.setFileName(user.getFileName());
        }

        return model.build();
    }
}
