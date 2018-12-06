package sashaVosu.firstWebApplication.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.UserConverters;
import sashaVosu.firstWebApplication.domain.ApplicationUser;
import sashaVosu.firstWebApplication.domain.UserSubscriptions;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.repo.SubscriptionRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriberService {

    private final UserRepo userRepo;

    private final SubscriptionRepo subscriptionRepo;

    public SubscriberService(UserRepo userRepo,
                             SubscriptionRepo subscriptionRepo
    ) {
        this.userRepo = userRepo;
        this.subscriptionRepo = subscriptionRepo;
    }

    //Subscribe to user
    public void subscribe(String currentUser, Long channelId) {

        ApplicationUser subscriber = userRepo.findOneByNickName(currentUser);

        UserSubscriptions subscribe = new UserSubscriptions();

        subscribe.setChannel(channelId);
        subscribe.setSubscriber(subscriber.getId());

        subscriptionRepo.save(subscribe);
    }

    //Unsubscribe from user
    @Transactional
    public void unsubscribe(String currentUser, Long channelId) {

        ApplicationUser subscriber = userRepo.findOneByNickName(currentUser);

        UserSubscriptions subscriptions = subscriptionRepo
                .findOneByChannelAndSubscriber(channelId, subscriber.getId());

        subscriptionRepo.delete(subscriptions);

    }

    //Show list of followers
    public List<UserModel> subscribersList(Long userId, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        List<Long> subscribersIdList = subscribersIdList(userId);

        Page<ApplicationUser> myFollowerList = userRepo.findAllByActiveAndIdIn(true, subscribersIdList,
                new PageRequest(page, size, Sort.Direction.ASC, "nickName"));

        return myFollowerList.getContent().stream()
                .map(UserConverters::toModel)
                .collect(Collectors.toList());
    }

    //Show list of subscriptions
    public List<UserModel> subscriptionsList(Long userId, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        List<Long> list = subscriptionsIdList(userId);

        Page<ApplicationUser> iFollow = userRepo.findAllByActiveAndIdIn(true, list,
                new PageRequest(page, size, Sort.Direction.ASC, "nickName"));

        return iFollow.getContent().stream()
                .map(UserConverters::toModel)
                .collect(Collectors.toList());
    }

    public int subscribersCount(Long userId) {

        List<Long> subscribersList = subscribersIdList(userId);

        return userRepo.findAllByActiveAndIdIn(true, subscribersList).size();
    }

    public int subscriptionsCount(Long userId) {

        List<Long> subscriptionsList = subscriptionsIdList(userId);

        return userRepo.findAllByActiveAndIdIn(true, subscriptionsList).size();
    }

    public boolean iFollow(String nickName, Long channelId){

        Long userId = userRepo.findOneByNickName(nickName).getId();

        UserSubscriptions subscribe = subscriptionRepo.findOneByChannelAndSubscriber(channelId, userId);

        return subscribe != null;
    }

    public boolean followMe(String nickName, Long channelId){

        Long userId = userRepo.findOneByNickName(nickName).getId();

        UserSubscriptions subscribe = subscriptionRepo.findOneByChannelAndSubscriber(userId, channelId);

        return subscribe != null;
    }

    List<Long> subscribersIdList(Long userId) {

        List<UserSubscriptions> subscribersList = subscriptionRepo.findAllByChannel(userId);

        return subscribersList.stream()
                .map(UserSubscriptions::getSubscriber)
                .collect(Collectors.toList());
    }

    List<Long> subscriptionsIdList(Long userId) {

        List<UserSubscriptions> subscriptionsList = subscriptionRepo.findAllBySubscriber(userId);

        return subscriptionsList.stream()
                .map(UserSubscriptions::getSubscriber)
                .collect(Collectors.toList());

    }
}
