package sashaVosu.firstWebApplication.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sashaVosu.firstWebApplication.converters.ReTweetConverters;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;

public class Utils {

    public static String getNickName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }

//Converts entity to tweetModel or reTweetModel
    public static TweetModel convert(Tweet tweet) {

        if (tweet.isReTweet()) {

            return ReTweetConverters.toModel(tweet);

        } else {

            return TweetConverters.toModel(tweet);
        }
    }


}
