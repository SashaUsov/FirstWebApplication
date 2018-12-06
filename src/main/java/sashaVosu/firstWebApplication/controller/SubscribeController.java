package sashaVosu.firstWebApplication.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.service.SubscriberService;
import sashaVosu.firstWebApplication.utils.Utils;

import java.util.List;

@RestController
@RequestMapping("subscriptions")
public class SubscribeController {

    private final SubscriberService subscriberService;

    public SubscribeController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    //Subscribe to user
    @PostMapping("{id}")
    public void subscribe(@PathVariable("id") Long channelId) {

        String nickName = Utils.getNickName();

        subscriberService.subscribe(nickName, channelId);
    }

    //Unsubscribe from user
    @DeleteMapping("{id}")
    public void unSubscribe(@PathVariable("id") Long channelId) {

        String nickName = Utils.getNickName();

        subscriberService.unsubscribe(nickName, channelId);
    }

    //Show list of followers
    @GetMapping("followers/{id}")
    public List<UserModel> showSubscribers(@PathVariable("id") Long userId,
                                           Pageable pageable
    ) {

        return subscriberService.subscribersList(userId, pageable);
    }

    //Show list of subscriptions
    @GetMapping("follow/{id}")
    public List<UserModel> showSubscriptions(@PathVariable("id") Long userId,
                                             Pageable pageable
    ) {

        return subscriberService.subscriptionsList(userId, pageable);
    }

    //user follow me count
    @GetMapping("follow-me/count/{id}")
    public int subscribersCount(@PathVariable("id") Long userId) {

        return  subscriberService.subscribersCount(userId);
    }

    //user count what i follow
    @GetMapping("i-follow/count/{id}")
    public int subscriptionsCount(@PathVariable("id") Long userId) {

        return  subscriberService.subscriptionsCount(userId);
    }
}
