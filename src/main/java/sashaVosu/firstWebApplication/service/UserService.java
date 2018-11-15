package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.UserConverters;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.exception.UserExistsException;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;
import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;

    private final TweetRepo tweetRepo;

    private final UserTweetLikesRepo userTweetLikesRepo;

    public UserService(UserRepo userRepo,
                       TweetRepo tweetRepo,
                       UserTweetLikesRepo userTweetLikesRepo)
    {
        this.userRepo = userRepo;
        this.tweetRepo = tweetRepo;
        this.userTweetLikesRepo = userTweetLikesRepo;
    }

//return list of all users
    public List<UserModel> getUserList() {
        return userRepo.findAll().stream()
                .map(UserConverters::toModel).collect(Collectors.toList());
    }

//create new user
    public UserModel userCreate(CreateUserModel model) {

        User userFromDb = userRepo.findOneByNickNameAndEmail(model.getNickName(), model.getEmail());

        if (userFromDb == null) {

            User newUser = UserConverters.toEntity(model);

            return UserConverters.toModel(userRepo.save(newUser));

        } else {

            throw new UserExistsException("User with this nickname or email already exists");
        }
    }

//delete user account and all user tweet and like from user-tweet-like table
//Deleted account cannot be recovered.
    public void deleteProfile(String nickName) {

        List<Tweet> tweetList = tweetRepo.findAllByCreator(nickName);

        List<Long> tweetIdList = tweetList.stream().map(Tweet::getId)
                .collect(Collectors.toList());

        Long userId = userRepo.findOneByNickName(nickName).getId();

        userTweetLikesRepo.deleteAllByTweetIdIn(tweetIdList);

        userTweetLikesRepo.deleteAllByUserId(userId);

        tweetRepo.deleteAllByFirstTweetIn(tweetList);

        tweetRepo.deleteAllByCreator(nickName);

        userRepo.deleteOneById(userId);
    }

    public UserModel getOneUser(Long id) {

        return UserConverters.toModel(userRepo.findOneById(id));
    }
}

