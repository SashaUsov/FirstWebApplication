package sashaVosu.firstWebApplication.controller;

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
    @GetMapping("subscribers/{id}")
    public List<UserModel> showSubscribers(@PathVariable("id") Long userId) {

        return subscriberService.subscribersList(userId);
    }

    //Show list of subscriptions
    @GetMapping("subscriptions/{id}")
    public List<UserModel> showSubscriptions(@PathVariable("id") Long userId) {

        return subscriberService.subscriptionsList(userId);
    }

}
