package sashaVosu.firstWebApplication.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_tweet_likes")
public class UserTweetLikes {

    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_tweet_likes_seq")
    private Long id;

    @Column(name = "tweet_id")
    private Long tweetId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "creation_data", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeWhenLiked;
}
