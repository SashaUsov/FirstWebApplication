package sashaVosu.firstWebApplication.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//dto class to create a relationship user-like-tweet. Inner class
@Getter
@Setter
public class CreateUserTweetLikesModel {

    private Long tweetId;

    private LocalDateTime timeWhenLiked;
}
