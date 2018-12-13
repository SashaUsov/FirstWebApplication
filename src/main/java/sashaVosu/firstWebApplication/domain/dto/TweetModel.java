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

    private int likeCount;

    private Boolean iLikeIt;

    public TweetModel() {
        iLikeIt = Boolean.FALSE;
        likeCount = 0;
    }

    public void setILikeIt(Boolean iLikeIt) {
        this.iLikeIt = iLikeIt;
    }

    private Integer reTweetCount;

    private boolean isReTweet;

    private String pic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TweetModel model = (TweetModel) o;

        if (likeCount != model.likeCount) return false;
        if (isReTweet != model.isReTweet) return false;
        if (!text.equals(model.text)) return false;
        if (!id.equals(model.id)) return false;
        if (!creator.equals(model.creator)) return false;
        if (!creationData.equals(model.creationData)) return false;
        if (iLikeIt != null ? !iLikeIt.equals(model.iLikeIt) : model.iLikeIt != null) return false;
        if (reTweetCount != null ? !reTweetCount.equals(model.reTweetCount) : model.reTweetCount != null) return false;
        return pic != null ? pic.equals(model.pic) : model.pic == null;
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + creator.hashCode();
        result = 31 * result + creationData.hashCode();
        result = 31 * result + likeCount;
        result = 31 * result + (iLikeIt != null ? iLikeIt.hashCode() : 0);
        result = 31 * result + (reTweetCount != null ? reTweetCount.hashCode() : 0);
        result = 31 * result + (isReTweet ? 1 : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        return result;
    }
}
