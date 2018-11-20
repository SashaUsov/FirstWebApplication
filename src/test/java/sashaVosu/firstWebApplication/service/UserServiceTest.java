package sashaVosu.firstWebApplication.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sashaVosu.firstWebApplication.Utils.TestUtil;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.exception.UserExistsException;
import sashaVosu.firstWebApplication.exception.UserNotFoundException;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;
import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private TweetRepo tweetRepo;

    @MockBean
    private UserTweetLikesRepo userTweetLikesRepo;

    @Test
    public void getUserList() throws Exception {

        List<UserModel> userModelList = TestUtil.getUserModelList();

        List<User> userList = TestUtil.getUserList();

        Mockito.doReturn(userList).when(userRepo).findAll();

        List<UserModel> modelListFromDb = userService.getUserList();

        Mockito.verify(userRepo, Mockito.times(1)).findAll();

        Assert.assertEquals(userModelList, modelListFromDb);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserListFail() throws Exception {

        Mockito.doThrow(new UserNotFoundException("Any user not found")).when(userRepo).findAll();

        List<UserModel> modelListFromDb = userService.getUserList();

        Mockito.verify(userRepo, Mockito.times(1)).findAll();
    }

    @Test(expected = UserExistsException.class)
    public void userCreateFail() throws Exception {

        CreateUserModel userModel = new CreateUserModel();

        userModel.setNickName("bob");
        userModel.setEmail("same@mail.com");


        Mockito.doReturn(new User()).when(userRepo)
                .findOneByNickNameAndEmail("bob", "same@mail.com");

        UserModel model = userService.userCreate(userModel);

        Mockito.verify(userRepo, Mockito.times(1))
                .findOneByNickNameAndEmail("bob", "same@mail.com");
    }

    @Test
    public void userCreate() throws Exception {

        CreateUserModel createUserModel = TestUtil.getCreateUserModel();

        User user = TestUtil.getUser();

        UserModel userModel = TestUtil.getUserModel();

        Mockito.doReturn(null).when(userRepo)
                .findOneByNickNameAndEmail("bob", "same@mail.com");

        Mockito.doReturn(user).when(userRepo)
                .save(any(User.class));

        UserModel model = userService.userCreate(createUserModel);

        Mockito.verify(userRepo, Mockito.times(1))
                .findOneByNickNameAndEmail("bob", "same@mail.com");

        Mockito.verify(userRepo, Mockito.times(1))
                .save(any(User.class));

       Assert.assertEquals(model, userModel);
    }

    @Test
    public void getOneUser() throws Exception {

        User user = TestUtil.getUser();

        UserModel userModel = TestUtil.getUserModel();

        Mockito.doReturn(user).when(userRepo).findOneById(1L);

        UserModel model = userService.getOneUser(1L);

        Mockito.verify(userRepo, Mockito.times(1))
                .findOneById(1L);
        Assert.assertEquals(userModel, model);
    }

    @Test
    public void getOneUserFail() throws Exception {

        Mockito.doReturn(null).when(userRepo).findOneById(21L);

        UserModel model = userService.getOneUser(21L);

        Mockito.verify(userRepo, Mockito.times(1))
                .findOneById(21L);

        Assert.assertEquals(null, model);
    }

    @Test
    public void deleteProfile() throws Exception {

        List<Tweet> tweetList = new ArrayList<>();

        Tweet tweet1 = new Tweet();

        tweet1.setId(1l);

        tweetList.add(tweet1);

        Mockito.doReturn(tweetList).when(tweetRepo).findAllByCreator(anyString());

        Mockito.doReturn(TestUtil.getUser()).when(userRepo).findOneByNickName("bob");

        Mockito.doNothing().when(userTweetLikesRepo).deleteAllByTweetIdIn(anyList());

        Mockito.doNothing().when(userTweetLikesRepo).deleteAllByUserId(anyLong());

        Mockito.doNothing().when(tweetRepo).deleteAllByFirstTweetIn(anyList());

        Mockito.doNothing().when(tweetRepo).deleteAllByCreator(anyString());

        Mockito.doNothing().when(userRepo).deleteOneById(anyLong());

        userService.deleteProfile("bob");

        Mockito.verify(userRepo, Mockito.times(1)).findOneByNickName("bob");

        Mockito.verify(userRepo, Mockito.times(1)).deleteOneById(anyLong());

        Mockito.verify(tweetRepo, Mockito.times(1)).findAllByCreator(anyString());

        Mockito.verify(tweetRepo, Mockito.times(1)).deleteAllByFirstTweetIn(anyList());

        Mockito.verify(tweetRepo, Mockito.times(1)).deleteAllByCreator(anyString());

        Mockito.verify(userTweetLikesRepo, Mockito.times(1)).deleteAllByTweetIdIn(anyList());

        Mockito.verify(userTweetLikesRepo, Mockito.times(1)).deleteAllByUserId(anyLong());
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteProfileFail() throws Exception {

        Mockito.doThrow(new UserNotFoundException("Not found")).when(userRepo).findOneByNickName("bob");

        userService.deleteProfile("bob");

        Mockito.verify(userRepo, Mockito.times(1)).findOneByNickName("bob");
    }
}