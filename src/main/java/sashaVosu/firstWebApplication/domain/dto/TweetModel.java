package sashaVosu.firstWebApplication.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TweetModel {

    private String text;

    private Long id;

    private String  creator;

    private LocalDateTime creationData;
}
