package sashaVosu.firstWebApplication.domain.dto;

import lombok.Getter;
import lombok.Setter;

//dto class to create a relationship user-like-tweet. Inner class
@Getter
@Setter
public class CreateUserTweetLikesModel {

    private Long tweetId;

//true if user like it, else - false
    private boolean like;
}
