package sashaVosu.firstWebApplication.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.service.SubscriberService;
import sashaVosu.firstWebApplication.utils.GetNickNameUtil;

import java.util.List;

@RestController
@RequestMapping("subscriptions")
public class SubscriptionsController {

    private final SubscriberService subscriberService;

    public SubscriptionsController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    //Subscribe to user
    @PostMapping("{id}")
    public void subscribe(@PathVariable("id") Long channelId) {

        String nickName = GetNickNameUtil.getNickName();

        subscriberService.subscribe(nickName, channelId);
    }

    //Unsubscribe from user
    @DeleteMapping("{id}")
    public void unSubscribe(@PathVariable("id") Long channelId) {

        String nickName = GetNickNameUtil.getNickName();

        subscriberService.unsubscribe(nickName, channelId);
    }


    //Show list of subscriptions
    @GetMapping("{id}")
    public List<Long> showSubscriptions(@PathVariable("id") Long userId,
                                        Pageable pageable
    ) {

        return subscriberService.subscriptionsList(userId, pageable);
    }

    //user count what i follow
    @GetMapping("count/{id}")
    public int subscriptionsCount(@PathVariable("id") Long userId) {

        return subscriberService.subscriptionsCount(userId);
    }

    //List of mutual subscriptions
    @GetMapping("mutual/{id}")
    public List<Long> mutualSubscriptions(@PathVariable("id") Long channelId,
                                          Pageable pageable
    ) {
        String nickName = GetNickNameUtil.getNickName();

        return subscriberService.mutualSubscriptions(nickName, channelId, pageable);
    }

    //Mutual subscriptions count
    @GetMapping("m-count/{id}")
    public int mutualSubsCount(@PathVariable("id") Long channelId) {

        String nickName = GetNickNameUtil.getNickName();

        return subscriberService.mutualSubsCount(nickName, channelId);
    }
}
