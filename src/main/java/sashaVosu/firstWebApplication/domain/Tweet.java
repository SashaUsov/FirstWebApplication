package sashaVosu.firstWebApplication.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @Column(name = "tweet_id", updatable=false, nullable=false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
