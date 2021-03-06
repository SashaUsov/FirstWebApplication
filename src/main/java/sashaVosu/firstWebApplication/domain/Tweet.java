package sashaVosu.firstWebApplication.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tweet")
@Getter
@Setter
public class Tweet {

    @Column(name = "text")
    @Length(max = 140, message = "Message to long")
    private String text;

    @Column(name = "creation_data", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationData;

    @Column(name = "creator", updatable = false)
    @NotNull
    private String creator;

    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tweet_sequence")
    private Long id;

    @Column(name = "is_re_tweet")
    private boolean reTweet;

    @Column(name = "pic_name")
    private String pic;

    @Column(name = "published")
    private boolean published;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "re_tweet")
    private Tweet firstTweet;

    @OneToMany(mappedBy = "firstTweet", fetch = FetchType.LAZY)
    private Set<Tweet> whoReTweet = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tweet_tag",
            joinColumns = {@JoinColumn(name = "tweet_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<HashTag> listTagsInTweet = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tweet_mark_user",
            joinColumns = {@JoinColumn(name = "tweet_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<ApplicationUser> markUserList = new HashSet<>();
}
