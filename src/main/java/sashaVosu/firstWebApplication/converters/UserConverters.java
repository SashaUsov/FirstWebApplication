package sashaVosu.firstWebApplication.converters;

import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;

public class UserConverters {

    public static User toEntity(CreateUserModel model) {
        User newUser = new User();
        newUser.setNickName(model.getNickName());
        newUser.setPassword(model.getPassword());
        newUser.setFirstName(model.getFirstName());
        newUser.setLastName(model.getLastName());
        newUser.setEmail(model.getEmail());
        newUser.setGender(model.getGender());
        newUser.setAge(model.getAge());

        return newUser;
    }

    public static UserModel toModel(User user) {

        UserModel model = new UserModel();

        model.setNickName(user.getNickName());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setId(user.getId());
        model.setGender(user.getGender());
        model.setAge(user.getAge());

        return model;
    }
}
