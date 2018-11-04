package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetRequest;
import sashaVosu.firstWebApplication.domain.dto.DeleteTweetRequest;
import sashaVosu.firstWebApplication.exception.NotAllowedLengthOfNicknameException;
import sashaVosu.firstWebApplication.exception.TweetNotFoundException;
import sashaVosu.firstWebApplication.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {

    private long id = 2;

    private final UserService userService;

    public TweetService(UserService userService) {
        this.userService = userService;
    }

    private List<Tweet> tweetsList = new ArrayList<Tweet>() {{
        Tweet first = new Tweet();
        first.setText("First message in object");
        first.setCreationData(LocalDateTime.now());
        first.setId(1l);
        add(first);
    }};

    public List<Tweet> getTweetsList() {
        return tweetsList;
    }

    public void setTweetsList(List<Tweet> tweetsList) {
        this.tweetsList = tweetsList;
    }

    public Tweet tweetCreate(CreateTweetRequest req, String nickName, String userPassword) {

        if (userService.isUserExist(nickName) && userService.isPasswordMatches(userPassword)) {

            if (req.getTweetText().length() > 1 && req.getTweetText().length() <= 140) {

                Tweet newOne = new Tweet();
                newOne.setText(req.getTweetText());
                newOne.setCreator(nickName);
                newOne.setCreationData(LocalDateTime.now());
                newOne.setId(id);
                id++;
                tweetsList.add(newOne);

                return newOne;

            } else {

                throw new NotAllowedLengthOfNicknameException("Not allowed length of nickname");
            }
        } else {

            throw new UserNotFoundException("User with this nickname not found");
        }
    }

    public void del(DeleteTweetRequest id, String nickName, String userPassword) {

        if (userService.isUserExist(nickName) && userService.isPasswordMatches(userPassword)) {

            for (Tweet tweet : tweetsList) {
                if (id.getId() == tweet.getId()) tweetsList.remove(tweet);
            }
        } else {

            throw new TweetNotFoundException("Tweet with id: " + id.getId() + " not found");
        }
    }
}
