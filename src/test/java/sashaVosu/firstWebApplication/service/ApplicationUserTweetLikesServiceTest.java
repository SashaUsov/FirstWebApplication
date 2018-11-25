package sashaVosu.firstWebApplication.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sashaVosu.firstWebApplication.Utils.TestUtil;
import sashaVosu.firstWebApplication.domain.ApplicationUser;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.UserTweetLikes;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;
import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationUserTweetLikesServiceTest {

    @Autowired
    private UserTweetLikesService userTweetLikesService;

    @MockBean
    private UserTweetLikesRepo userTweetLikesRepo;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private TweetRepo tweetRepo;

    @Test(expected = Exception.class)
    public void shouldPutLikeOnTweetWithId1() throws Exception {

        long givenTweetId = 1L;

        String givenNickName = "bob";

        ApplicationUser givenUser = TestUtil.returnUserWithId1();


        doReturn(givenUser).when(userRepo).findOneByNickName(anyString());

        doThrow(new RuntimeException()).when(userTweetLikesRepo).save(any(UserTweetLikes.class));

        userTweetLikesService.like(givenTweetId, givenNickName);

        verify(userRepo).findOneByNickName(anyString());

        verify(userTweetLikesRepo).save(any(UserTweetLikes.class));
    }

    @Test(expected = Exception.class)
    public void shouldRemoveLikeFromTweetWithId1() throws Exception {

        long givenTweetId = 1L;

        String givenNickName = "bob";

        ApplicationUser givenUser = TestUtil.returnUserWithId1();

        doReturn(givenUser).when(userRepo).findOneByNickName(anyString());

        doThrow(new RuntimeException()).when(userTweetLikesRepo).deleteByUserIdAndTweetId(anyLong(),anyLong());

        userTweetLikesService.unlike(givenTweetId, givenNickName);

        verify(userRepo).findOneByNickName(anyString());

        verify(userTweetLikesRepo).deleteByUserIdAndTweetId(anyLong(),anyLong());
    }

    @Test
    public void shouldReturnTweetModelWithLikeStatistic() {

        List<UserTweetLikes> givenUserTweetLikesList = TestUtil.returnListUserTweetLikesWhereTwetWithId1Has2Like();

        List<ApplicationUser> givenUserList = TestUtil.returnListOfUserWithId1AndId2();

        String givenNickName = "bob";

        TweetModel givenTweetModel = TestUtil.returnTweetModelWithId1();

        TweetModel expectedTweetModel = TestUtil.returnTweetModelWithLikeStatistic();

        doReturn(givenUserTweetLikesList).when(userTweetLikesRepo).findAllByTweetId(anyLong());

        doReturn(givenUserList).when(userRepo).findAllByIdIn(anyCollection());

        TweetModel actual = userTweetLikesService.likeStatistic(givenTweetModel, givenNickName);

        verify(userRepo).findAllByIdIn(anyCollection());

        verify(userTweetLikesRepo, times(2)).findAllByTweetId(anyLong());

        assertEquals(expectedTweetModel, actual);
    }

    @Test
    public void shouldReturnTweetModelListWhatLikeUserWithNickNameBob() {

        String givenNickName = "bob";

        List<UserTweetLikes> givenUserTweetLikesList = TestUtil.returnUserTweetLikesListWhatLikeUserWithNickNameBob();

        List<Tweet> givenTweetList = TestUtil.returnTweetListWhatLikeUserWithNickNameBob();

        List<TweetModel> expectedTweetModelList = TestUtil.returnTweetModelListWhatLikeUserWithNickNameBob();

        ApplicationUser givenUser = TestUtil.returnUserWithId1();

        //likeStatistic method

        List<UserTweetLikes> givenUserTweetLikesList2 = TestUtil.returnListUserTweetLikesWhereTwetWithId1Has2Like();

        List<ApplicationUser> givenUserList = TestUtil.returnListOfUserWithId1AndId2();

        doReturn(givenUserTweetLikesList2 ).when(userTweetLikesRepo).findAllByTweetId(anyLong());

        doReturn(givenUserList).when(userRepo).findAllByIdIn(anyCollection());

        ////////////////

        doReturn(givenUserTweetLikesList).when(userTweetLikesRepo).findAllByUserId(anyLong());

        doReturn(givenTweetList).when(tweetRepo).findAllByIdIn(anyCollection());

        doReturn(givenUser).when(userRepo).findOneByNickName(givenNickName);

        List<TweetModel> actual = userTweetLikesService.tweetWhatLike(givenNickName);

        assertEquals(expectedTweetModelList, actual);
    }
}