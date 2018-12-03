package sashaVosu.firstWebApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.service.TweetService;

import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController {

    private final TweetService tweetService;

    public TagController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    //get tweet list by tag
    @GetMapping("{tag}")
    public List<TweetModel> getTweetListByTag(@PathVariable("tag") String tagToFind) {

        return tweetService.getTweetListByTag(tagToFind);
    }
}
