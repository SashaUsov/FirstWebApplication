package sashaVosu.firstWebApplication.utils;

import sashaVosu.firstWebApplication.domain.Tweet;

public class DeleteTweetUtil {

    public static Tweet deactivateTweet(Tweet tweet) {

        tweet.setPublished(false);

        return tweet;
    }
}
