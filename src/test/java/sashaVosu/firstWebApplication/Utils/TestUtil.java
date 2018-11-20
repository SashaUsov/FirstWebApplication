package sashaVosu.firstWebApplication.Utils;

import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static CreateUserModel getCreateUserModel() {

        CreateUserModel createUserModel = new CreateUserModel();

        createUserModel.setNickName("bob");
        createUserModel.setEmail("same@mail.com");
        createUserModel.setFirstName("rot");
        createUserModel.setLastName("tot");
        createUserModel.setGender("male");
        createUserModel.setAge("22");
        createUserModel.setPassword("1234567");

        return createUserModel;
    }

    public static User getUser() {

        User user = new User();

        user.setId(1L);
        user.setFirstName("rot");
        user.setLastName("tot");
        user.setNickName("bob");
        user.setGender("male");
        user.setAge("22");
        user.setEmail("same@mail.com");

        return user;
    }

    public static UserModel getUserModel() {

        UserModel userModel = new UserModel();

        userModel.setSubscriptionsCount(0);
        userModel.setSubscribersCount(0);
        userModel.setNickName("bob");
        userModel.setFirstName("rot");
        userModel.setLastName("tot");
        userModel.setGender("male");
        userModel.setAge("22");
        userModel.setEmail("same@mail.com");
        userModel.setId(1L);

        return userModel;
    }

    public static List<User> getUserList() {

        User user = new User();

        user.setId(1L);
        user.setFirstName("rot");
        user.setLastName("tot");
        user.setNickName("bob");
        user.setGender("male");
        user.setAge("22");
        user.setEmail("same@mail.com");

        User user2 = new User();

        user2.setId(2L);
        user2.setFirstName("Usr");
        user2.setLastName("Usr");
        user2.setNickName("Usr");
        user2.setGender("male");
        user2.setAge("25");
        user2.setEmail("samemail@mail.com");

        List<User> userList = new ArrayList<>();

        userList.add(user);
        userList.add(user2);

        return userList;
    }

    public static List<UserModel> getUserModelList() {

        List<UserModel> userModelList = new ArrayList<>();

        UserModel userModel = new UserModel();

        userModel.setSubscriptionsCount(0);
        userModel.setSubscribersCount(0);
        userModel.setNickName("bob");
        userModel.setFirstName("rot");
        userModel.setLastName("tot");
        userModel.setGender("male");
        userModel.setAge("22");
        userModel.setEmail("same@mail.com");
        userModel.setId(1L);

        userModelList.add(userModel);

        UserModel userModel2 = new UserModel();

        userModel2.setSubscriptionsCount(0);
        userModel2.setSubscribersCount(0);
        userModel2.setNickName("Usr");
        userModel2.setFirstName("Usr");
        userModel2.setLastName("Usr");
        userModel2.setGender("male");
        userModel2.setAge("25");
        userModel2.setEmail("samemail@mail.com");
        userModel2.setId(2L);

        userModelList.add(userModel2);

        return userModelList;
    }
}
