package sashaVosu.firstWebApplication.converters;

import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.ReTweetModel;

public class ReTweetConverters {

    public static ReTweetModel toModel(Tweet tweet) {

        ReTweetModel model = new ReTweetModel();

        model.setCreationData(tweet.getCreationData());
        model.setCreator(tweet.getCreator());
        model.setId(tweet.getId());
        model.setText(tweet.getText());
        model.setReTweetCount(tweet.getWhoReTweet().size());
        model.setReTweet(tweet.isReTweet());
        model.setOriginalCreator(tweet.getFirstTweet().getCreator());
        model.setOriginalId(tweet.getFirstTweet().getId());
        model.setOriginalText(tweet.getFirstTweet().getText());
        model.setOriginalData(tweet.getFirstTweet().getCreationData());

        return model;
    }
}
