package sashaVosu.firstWebApplication.Utils;

import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.UserTweetLikes;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static CreateUserModel returnCreateUserModel() {

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

    public static User returnUserWithId1() {

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

    public static UserModel returnUserModelWitgId1() {

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

    public static List<User> returnListOfUserWithId1AndId2() {

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

    public static List<UserModel> returnListOfUserModelWithId1AndId2() {

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

    public static List<UserTweetLikes> returnListUserTweetLikesWhereTwetWithId1Has2Like() {

        List<UserTweetLikes> userModelList = new ArrayList<>();

        UserTweetLikes model1 = new UserTweetLikes();

        model1.setId(1L);
        model1.setUserId(1L);
        model1.setTweetId(1L);

        userModelList.add(model1);

        UserTweetLikes model2 = new UserTweetLikes();

        model1.setId(2L);
        model1.setUserId(2L);
        model1.setTweetId(1L);

        userModelList.add(model2);

        return userModelList;
    }

    public static TweetModel returnTweetModekWithId1() {

        TweetModel model = new TweetModel();

        model.setId(1L);

        return model;
    }

    public static TweetModel returnTweetModelWithId1() {

        TweetModel model = new TweetModel();

        model.setId(1L);

        return model;
    }

    public static TweetModel returnTweetModelWithLikeStatistic() {

        TweetModel model = new TweetModel();

        model.setId(1L);
        model.setLikeCount(2L);
        model.setILikeIt(true);
        model.setReTweet(false);

        return model;
    }

    public static List<UserTweetLikes> returnUserTweetLikesListWhatLikeUserWithNickNameBob() {

        List<UserTweetLikes> userModelList = new ArrayList<>();

        UserTweetLikes model1 = new UserTweetLikes();

        model1.setId(1L);
        model1.setUserId(1L);
        model1.setTweetId(1L);

        userModelList.add(model1);

        return  userModelList;
    }

    public static List<Tweet> returnTweetListWhatLikeUserWithNickNameBob() {

        List<Tweet> tweetList = new ArrayList<>();

        Tweet tweet = new Tweet();

        tweet.setId(1L);
        tweet.setCreator("bob");


        tweetList.add(tweet);

        return tweetList;
    }

    public static List<TweetModel> returnTweetModelListWhatLikeUserWithNickNameBob() {

        List<TweetModel> tweetModelList = new ArrayList<>();

        TweetModel tweetModel = new TweetModel();

        tweetModel.setId(1L);
        tweetModel.setCreator("bob");
        tweetModel.setLikeCount(2L);
        tweetModel.setILikeIt(true);
        tweetModel.setReTweet(false);
        tweetModel.setReTweetCount(0);

        tweetModelList.add(tweetModel);

        return tweetModelList;
    }
}
