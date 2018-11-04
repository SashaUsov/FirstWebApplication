package sashaVosu.firstWebApplication.domain.dto;


import javax.validation.constraints.NotNull;


public class CreateTweetRequest {
    private String tweetText;

    @NotNull
    private String creator;

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
