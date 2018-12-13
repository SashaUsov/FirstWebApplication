package sashaVosu.firstWebApplication.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sashaVosu.firstWebApplication.service.SubscriberService;
import sashaVosu.firstWebApplication.utils.GetNickNameUtil;

import java.util.List;

@RestController
@RequestMapping("subscribers")
public class SubscribersController {
    private final SubscriberService subscriberService;

    public SubscribersController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    //Show list of subscribers
    @GetMapping("{id}")
    public List<Long> showSubscribers(@PathVariable("id") Long userId,
                                      Pageable pageable
    ) {

        return subscriberService.subscribersList(userId, pageable);
    }

    //user follow me count
    @GetMapping("count/{id}")
    public int subscribersCount(@PathVariable("id") Long userId) {

        return subscriberService.subscribersCount(userId);
    }

    //List of mutual subscribers
    @GetMapping("mutual/{id}")
    public List<Long> mutualSubscribers(@PathVariable("id") Long channelId,
                                        Pageable pageable
    ) {
        String nickName = GetNickNameUtil.getNickName();

        return subscriberService.mutualSubscribers(nickName, channelId, pageable);
    }

    //Mutual subscribers count
    @GetMapping("m-count/{id}")
    public int mutualFollowCount(@PathVariable("id") Long channelId) {

        String nickName = GetNickNameUtil.getNickName();

        return subscriberService.mutualFollowCount(nickName, channelId);
    }
}
