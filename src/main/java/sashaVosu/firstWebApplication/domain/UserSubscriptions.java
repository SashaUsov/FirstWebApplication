package sashaVosu.firstWebApplication.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_subscriptions")
public class UserSubscriptions {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_sequence")
    private Long id;

    @Column(name = "channel_id")
    private Long channel;

    @Column(name = "subscriber_id")
    private Long subscriber;

    @Column(name = "subscription_time", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime subscriptionTime;
}
