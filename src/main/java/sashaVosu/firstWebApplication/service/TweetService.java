package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.exception.NotAllowedLengthOfTextException;
import sashaVosu.firstWebApplication.exception.TweetNotFoundException;
import sashaVosu.firstWebApplication.exception.UserNotFoundException;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;

import java.util.List;

@Service
public class TweetService {

    private final UserService userService;

    private final TweetRepo tweetRepo;

    private final UserRepo userRepo;

    public TweetService(UserService userService, TweetRepo tweetRepo, UserRepo userRepo) {
        this.userService = userService;
        this.tweetRepo = tweetRepo;
        this.userRepo = userRepo;
    }

    public List<Tweet> getTweetsList() {

        return tweetRepo.findAll();
    }

    public TweetModel tweetCreate(CreateTweetModel tweetModel, String nickName, String userPassword){
        if (userRepo.existsByNickName(nickName) && userRepo.existsByPassword(userPassword)) {
            if (tweetModel.getTweetText().length() >= 1 && tweetModel.getTweetText().length() <= 140) {

                Tweet newTweet = TweetConverters.toEntity(tweetModel, nickName);

                return TweetConverters.toModel(tweetRepo.save(newTweet));

            } else {

                throw new NotAllowedLengthOfTextException("Not allowed length of tweet");
            }
        } else {

            throw new UserNotFoundException("User with this nickname or password not found");
        }
    }

    public void del(Long id, String nickName, String userPassword) {
        if (userRepo.existsByNickName(nickName) && userRepo.existsByPassword(userPassword)) {

            tweetRepo.deleteById(id);
        } else {

            throw new TweetNotFoundException("Tweet with id: " + id + " not found");
        }
    }
}
