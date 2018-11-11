package sashaVosu.firstWebApplication.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_tweet_likes")
public class UserTweetLikes {

    @Column(name = "id", updatable=false, nullable=false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tweet_id")
    private Long tweetId;

    @Column(name = "user_id")
    private Long userId;

//true if user like it, else - false
    @Column(name = "likes")
    private boolean like;
}
