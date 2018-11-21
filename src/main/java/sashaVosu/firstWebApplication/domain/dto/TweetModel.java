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

    public void setILikeIt(Boolean iLikeIt) {
        this.iLikeIt = iLikeIt;
    }

    private Integer reTweetCount;

    private boolean isReTweet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TweetModel model = (TweetModel) o;

        if (isReTweet != model.isReTweet) return false;
        if (text != null ? !text.equals(model.text) : model.text != null) return false;
        if (!id.equals(model.id)) return false;
        if (creator != null ? !creator.equals(model.creator) : model.creator != null) return false;
        if (creationData != null ? !creationData.equals(model.creationData) : model.creationData != null) return false;
        if (likeCount != null ? !likeCount.equals(model.likeCount) : model.likeCount != null) return false;
        if (iLikeIt != null ? !iLikeIt.equals(model.iLikeIt) : model.iLikeIt != null) return false;
        return reTweetCount != null ? reTweetCount.equals(model.reTweetCount) : model.reTweetCount == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + id.hashCode();
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (creationData != null ? creationData.hashCode() : 0);
        result = 31 * result + (likeCount != null ? likeCount.hashCode() : 0);
        result = 31 * result + (iLikeIt != null ? iLikeIt.hashCode() : 0);
        result = 31 * result + (reTweetCount != null ? reTweetCount.hashCode() : 0);
        result = 31 * result + (isReTweet ? 1 : 0);
        return result;
    }
}
