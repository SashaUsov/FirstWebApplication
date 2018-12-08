package sashaVosu.firstWebApplication.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.domain.ApplicationUser;
import sashaVosu.firstWebApplication.domain.UserSubscriptions;
import sashaVosu.firstWebApplication.repo.SubscriptionRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

        subscribe.setSubscriptionTime(LocalDateTime.now());
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
    public List<Long> subscribersList(Long userId, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        List<Long> subscribersIdList = subscribersIdList(userId);

        Page<ApplicationUser> myFollowerList = userRepo.findAllByActiveAndIdIn(true, subscribersIdList,
                new PageRequest(page, size, Sort.Direction.ASC, "nickName"));

        return myFollowerList.getContent().stream()
                .map(ApplicationUser::getId)
                .collect(Collectors.toList());
    }

    //Show list of subscriptions
    public List<Long> subscriptionsList(Long userId, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        List<Long> list = subscriptionsIdList(userId);

        Page<ApplicationUser> iFollow = userRepo.findAllByActiveAndIdIn(true, list,
                new PageRequest(page, size, Sort.Direction.ASC, "nickName"));

        return iFollow.getContent().stream()
                .map(ApplicationUser::getId)
                .collect(Collectors.toList());
    }

    //user follow me count
    public int subscribersCount(Long userId) {

        List<Long> subscribersList = subscribersIdList(userId);

        return userRepo.findAllByActiveAndIdIn(true, subscribersList).size();
    }

    //user count what i follow
    public int subscriptionsCount(Long userId) {

        List<Long> subscriptionsList = subscriptionsIdList(userId);

        return userRepo.findAllByActiveAndIdIn(true, subscriptionsList).size();
    }

    //Do I subscribe to this user
    public boolean iFollow(String nickName, Long channelId){

        Long userId = myId(nickName);

        UserSubscriptions subscribe = subscriptionRepo.findOneByChannelAndSubscriber(channelId, userId);

        return subscribe != null;
    }

    //Is this user following me
    public boolean followMe(String nickName, Long channelId){

        Long userId = myId(nickName);

        UserSubscriptions subscribe = subscriptionRepo.findOneByChannelAndSubscriber(userId, channelId);

        return subscribe != null;
    }

    //List of mutual subscriptions
    public List<Long> mutualSubscriptions(String nickName,
                                          Long channelId,
                                          Pageable pageable
    ) {
        Long userId = myId(nickName);

        String channelName = userRepo.findOneById(channelId).getNickName();

        List<Long> mySubscriptions = subscriptionsList(userId, pageable);

        for(Long x : mySubscriptions){
            System.out.println(x);
        }
        return mySubscriptions.stream().filter(a -> iFollow(channelName,a))
                .collect(Collectors.toList());
    }

    //List of mutual followers
    public List<Long> mutualSubscribers(String nickName,
                                        Long channelId,
                                        Pageable pageable
    ) {
        Long userId = myId(nickName);

        String channelName = userRepo.findOneById(channelId).getNickName();

        List<Long> mySubscribers = subscribersList(userId, pageable);

        return mySubscribers.stream().filter(a -> followMe(channelName, a))
                .collect(Collectors.toList());
    }

    //Mutual subscriptions count
    public int mutualSubsCount(String nickName,
                               Long channelId
    ){
        return mutualSubscriptions(nickName,
                channelId,
                new PageRequest(1, Integer.MAX_VALUE, Sort.Direction.ASC, "nickName"))
                .size();
    }

    //Mutual followers count
    public int mutualFollowCount(String nickName,
                                 Long channelId
    ){
        return mutualSubscribers(nickName,
                channelId,
                new PageRequest(1, Integer.MAX_VALUE, Sort.Direction.ASC, "nickName"))
                .size();
    }

    private Long myId(String nickName) {

        return userRepo.findOneByNickName(nickName).getId();
    }

    private List<Long> subscribersIdList(Long userId) {

        List<UserSubscriptions> subscribersList = subscriptionRepo.findAllByChannel(userId);

        return subscribersList.stream()
                .map(UserSubscriptions::getSubscriber)
                .collect(Collectors.toList());
    }

    private List<Long> subscriptionsIdList(Long userId) {

        List<UserSubscriptions> subscriptionsList = subscriptionRepo.findAllBySubscriber(userId);

        return subscriptionsList.stream()
                .map(UserSubscriptions::getChannel)
                .collect(Collectors.toList());
    }
}
