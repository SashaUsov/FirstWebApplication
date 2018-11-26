package sashaVosu.firstWebApplication.domain.dto;

import lombok.Getter;
import lombok.Setter;

//dto class to create tweet entity from model derived from the frontend
@Getter
@Setter
public class CreateTweetModel {

    private String tweetText;

    private String resultFileName;

}
