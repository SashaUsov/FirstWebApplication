package sashaVosu.firstWebApplication.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tweet")
@Getter
@Setter
public class Tweet {

    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationData;

    @NotNull
    private String creator;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
