package sashaVosu.firstWebApplication.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//dto class to create ReTweetModel model from entity and give back to frontend

@Getter
@Setter
public class ReTweetModel extends TweetModel {

    private String originalText;

    private Long originalId;

    private String originalCreator;

    private LocalDateTime originalData;
}
