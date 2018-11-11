package sashaVosu.firstWebApplication.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//dto class to create tweet model from entity and give back to frontend
@Getter
@Setter
public class TweetModel {

    private String text;

    private Long id;

    private String creator;

    private LocalDateTime creationData;

    private Long likeCount;

    private Boolean iLikeIt;

    public TweetModel() {
        iLikeIt = Boolean.FALSE;
        likeCount = 0L;
    }


    public void setiLikeIt(Boolean iLikeIt) {
        this.iLikeIt = iLikeIt;
    }
}
