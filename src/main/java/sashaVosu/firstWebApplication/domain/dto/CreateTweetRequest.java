package sashaVosu.firstWebApplication.domain.dto;


import javax.validation.constraints.NotNull;


public class CreateTweetRequest {
    private String tweetText;

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

}
