//package sashaVosu.firstWebApplication.service;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import sashaVosu.firstWebApplication.Utils.TestUtil;
//import sashaVosu.firstWebApplication.domain.ApplicationUser;
//import sashaVosu.firstWebApplication.domain.Tweet;
//import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
//import sashaVosu.firstWebApplication.domain.dto.UserModel;
//import sashaVosu.firstWebApplication.exception.UserExistsException;
//import sashaVosu.firstWebApplication.exception.UserNotFoundException;
//import sashaVosu.firstWebApplication.repo.TweetRepo;
//import sashaVosu.firstWebApplication.repo.UserRepo;
//import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ApplicationUserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @MockBean
//    private UserRepo userRepo;
//
//    @MockBean
//    private TweetRepo tweetRepo;
//
//    @MockBean
//    private UserTweetLikesRepo userTweetLikesRepo;
//
//    @Test
//    public void shouldReturnUserModelList() {
//
//        List<UserModel> expectedUserModelList = TestUtil.returnListOfUserModelWithId1AndId2();
//
//        List<ApplicationUser> givenUserList = TestUtil.returnListOfUserWithId1AndId2();
//
//        doReturn(givenUserList).when(userRepo).findAll();
//
////        List<UserModel> actual = userService.getUserList();
//
//        verify(userRepo).findAll();
//
////        Assert.assertEquals(expectedUserModelList, actual);
//    }
//
//    @Test(expected = UserNotFoundException.class)
//    public void shouldThrowExceptionWhenGetUserList() {
//
//        doThrow(new UserNotFoundException("Any user not found")).when(userRepo).findAll();
//
////        List<UserModel> modelListFromDb = userService.getUserList();
//
//        verify(userRepo).findAll();
//    }
//
//    @Test(expected = UserExistsException.class)
//    public void shouldThrowExceptionOnCreateUser() {
//
//        CreateUserModel givenCreateUserModel = new CreateUserModel();
//
//        givenCreateUserModel.setNickName("bob");
//        givenCreateUserModel.setEmail("same@mail.com");
//
//
//        doReturn(new ApplicationUser()).when(userRepo)
//                .findOneByNickNameAndEmail(givenCreateUserModel.getNickName(), givenCreateUserModel.getEmail());
//
//        UserModel actual = userService.userCreate(givenCreateUserModel);
//
//        verify(userRepo)
//                .findOneByNickNameAndEmail(givenCreateUserModel.getNickName(), givenCreateUserModel.getEmail());
//    }
//
//    @Test
//    public void shouldCreateUserAndReturnUserModelWithId1() {
//
//        CreateUserModel givenCreateUserModel = TestUtil.returnCreateUserModel();
//
//        ApplicationUser givenUser = TestUtil.returnUserWithId1();
//
//        UserModel expectedUserModel = TestUtil.returnUserModelWitgId1();
//
//        doReturn(null).when(userRepo)
//                .findOneByNickNameAndEmail(givenCreateUserModel.getNickName(), givenCreateUserModel.getEmail());
//
//        doReturn(givenUser).when(userRepo)
//                .save(any(ApplicationUser.class));
//
//        UserModel actual = userService.userCreate(givenCreateUserModel);
//
//        verify(userRepo)
//                .findOneByNickNameAndEmail(givenCreateUserModel.getNickName(), givenCreateUserModel.getEmail());
//
//        verify(userRepo).save(any(ApplicationUser.class));
//
//       assertEquals(expectedUserModel, actual);
//    }
//
//    @Test
//    public void shouldFindUserWithId1() {
//
//        long givenUserId = 1L;
//
//        ApplicationUser givenUser = TestUtil.returnUserWithId1();
//
//        UserModel expectedUserModel = TestUtil.returnUserModelWitgId1();
//
//        doReturn(givenUser).when(userRepo).findOneById(givenUserId);
//
//        UserModel actual = userService.getOneUser(givenUserId);
//
//        assertEquals(expectedUserModel, actual);
//    }
//
//    @Test
//    public void shouldReturnNullOnGetOneUserById21() {
//
//        long givenUserId = 21L;
//
//        Mockito.doReturn(null).when(userRepo).findOneById(givenUserId);
//
//        UserModel actual = userService.getOneUser(givenUserId);
//
//        verify(userRepo).findOneById(givenUserId);
//
//        assertNull(null, actual);
//    }
//
//    @Test
//    public void deleteProfile() throws Exception {
//
//        List<Tweet> tweetList = new ArrayList<>();
//
//        Tweet tweet1 = new Tweet();
//
//        tweet1.setId(1l);
//
//        tweetList.add(tweet1);
//
////        doReturn(tweetList).when(tweetRepo).findAllByCreator(anyString());
//
//        doReturn(TestUtil.returnUserWithId1()).when(userRepo).findOneByNickName("bob");
//
//        doNothing().when(userTweetLikesRepo).deleteAllByTweetIdIn(anyList());
//
//        doNothing().when(userTweetLikesRepo).deleteAllByUserId(anyLong());
//
//        doNothing().when(tweetRepo).deleteAllByFirstTweetIn(anyList());
//
//        doNothing().when(tweetRepo).deleteAllByCreator(anyString());
//
//        doNothing().when(userRepo).deleteOneById(anyLong());
//
//        userService.deleteProfile("bob");
//
//        verify(userRepo).findOneByNickName("bob");
//
//        verify(userRepo).deleteOneById(anyLong());
//
////        verify(tweetRepo).findAllByCreator(anyString());
//
//        verify(tweetRepo).deleteAllByFirstTweetIn(anyList());
//
//        verify(tweetRepo).deleteAllByCreator(anyString());
//
//        verify(userTweetLikesRepo).deleteAllByTweetIdIn(anyList());
//
//        verify(userTweetLikesRepo).deleteAllByUserId(anyLong());
//    }
//
//    @Test(expected = UserNotFoundException.class)
//    public void deleteProfileFail() throws Exception {
//
//        doThrow(new UserNotFoundException("Not found")).when(userRepo).findOneByNickName("bob");
//
//        userService.deleteProfile("bob");
//
//        verify(userRepo, Mockito.times(1)).findOneByNickName("bob");
//    }
//}