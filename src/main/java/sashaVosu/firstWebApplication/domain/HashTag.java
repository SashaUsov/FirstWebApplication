package sashaVosu.firstWebApplication.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "hash_tag")
public class HashTag {

    @Column(name = "tag")
    private String tag;

    @Column(name = "id", updatable=false, nullable=false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "tweet_tag",
            joinColumns = { @JoinColumn(name = "tag_id") },
            inverseJoinColumns = { @JoinColumn(name = "tweet_id") }
    )
    private Set<Tweet> tweetWithTagList = new HashSet<>();
}
