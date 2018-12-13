package sashaVosu.firstWebApplication.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.fasades.TweetFacades;
import sashaVosu.firstWebApplication.service.ReTweetService;
import sashaVosu.firstWebApplication.service.TweetService;
import sashaVosu.firstWebApplication.utils.GetNickNameUtil;

import java.util.List;

@RestController
@RequestMapping("re-tweet")
public class ReTweetController {

    private final ReTweetService reTweetService;
    private final TweetService tweetService;
    private final TweetFacades tweetFacades;

    public ReTweetController(ReTweetService reTweetService,
                             TweetService tweetService,
                             TweetFacades tweetFacades
    ) {
        this.reTweetService = reTweetService;
        this.tweetService = tweetService;
        this.tweetFacades = tweetFacades;
    }

    //Repost tweet user to your page
    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetModel createTweet(@RequestBody CreateTweetModel model,
                                  @PathVariable("id") Long tweetId
    ) {
        String nickName = GetNickNameUtil.getNickName();

        return reTweetService.reTweet(nickName, model, tweetId);
    }

    //Get a list of tweets containing the original message
    @GetMapping("{id}")
    public List<TweetModel> tweetShare(@PathVariable("id") Long tweetId) {

        return reTweetService.listOfReTweets(tweetId);
    }

    //Tweets shared by this user
    @GetMapping
    public List<TweetModel> tweetsUserShared(Pageable pageable) {

        String nickName = GetNickNameUtil.getNickName();

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        List<TweetModel> modelList = tweetService.getTweetsList(new PageRequest(page, size,
                Sort.Direction.DESC, "creationData"));

        List<TweetModel> listWithLike = tweetFacades.getTweetsList(nickName, modelList);

        return tweetService.myReTweetList(listWithLike);
    }

}
