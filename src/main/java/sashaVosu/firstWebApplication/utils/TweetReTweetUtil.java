package sashaVosu.firstWebApplication.utils;

import sashaVosu.firstWebApplication.converters.ReTweetConverters;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;

public class TweetReTweetUtil {

    //Converts entity to tweetModel or reTweetModel
    public static TweetModel convert(Tweet tweet) {

        if (tweet.isReTweet()) {

            return ReTweetConverters.toModel(tweet);

        } else {

            return TweetConverters.toModel(tweet);
        }
    }
}
