package sashaVosu.firstWebApplication.converters;

import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;

import java.time.LocalDateTime;

public class TweetConverters {

//method helper. convert tweet dto class to tweet entity
    public static Tweet toEntity(CreateTweetModel tweetModel, String nickName) {

        Tweet newTweet = new Tweet();

        newTweet.setText(tweetModel.getTweetText());
        newTweet.setCreator(nickName);
        newTweet.setCreationData(LocalDateTime.now());


        return newTweet;
    }

//method helper. convert tweet entity to tweet dto model
    public static TweetModel toModel(Tweet tweet) {

        TweetModel model = new TweetModel();

        model.setCreationData(tweet.getCreationData());
        model.setCreator(tweet.getCreator());
        model.setId(tweet.getId());
        model.setText(tweet.getText());
        model.setReTweetCount(tweet.getWhoReTweet().size());
        model.setReTweet(tweet.isReTweet());

        return model;
    }
}
