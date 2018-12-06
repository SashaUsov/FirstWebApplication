package sashaVosu.firstWebApplication.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import sashaVosu.firstWebApplication.domain.UserSubscriptions;

import java.util.List;

public interface SubscriptionRepo extends PagingAndSortingRepository<UserSubscriptions, Long> {

    UserSubscriptions findOneByChannelAndSubscriber(Long channelId, Long subscriberId);

    List<UserSubscriptions> findAllByChannel(Long channelId);

    List<UserSubscriptions> findAllBySubscriber(Long userId);
}

