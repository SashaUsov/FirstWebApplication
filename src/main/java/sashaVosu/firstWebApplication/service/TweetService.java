package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetRequest;
import sashaVosu.firstWebApplication.exception.NotAllowedLengthOfNicknameException;
import sashaVosu.firstWebApplication.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {

    private final UserService userService;

    public TweetService(UserService userService) {
        this.userService = userService;
    }

    private List<Tweet> tweetsList = new ArrayList<Tweet>() {{
        Tweet first = new Tweet();
        first.setText("First message in object");
        first.setCreationData(LocalDateTime.now());
        add(first);
    }};

    public List<Tweet> getTweetsList() {
        return tweetsList;
    }

    public void setTweetsList(List<Tweet> tweetsList) {
        this.tweetsList = tweetsList;
    }

    public Tweet tweetCreate(CreateTweetRequest req) {
        if (userService.isUserExist(req.getCreator())) {

            if (req.getTweetText().length() > 1 && req.getTweetText().length() <= 140) {

                Tweet newOne = new Tweet();
                newOne.setText(req.getTweetText());
                newOne.setCreator(req.getCreator());
                newOne.setCreationData(LocalDateTime.now());
                tweetsList.add(newOne);

                return newOne;

            } else {

                throw new NotAllowedLengthOfNicknameException("Not allowed length of nickname");
            }
        } else {

            throw new UserNotFoundException("User with this nickname not found");
        }
    }
}
