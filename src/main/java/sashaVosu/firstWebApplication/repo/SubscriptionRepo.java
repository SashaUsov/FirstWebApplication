package sashaVosu.firstWebApplication.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import sashaVosu.firstWebApplication.domain.UserSubscriptions;
import sashaVosu.firstWebApplication.domain.dto.RecommendSubscribe;

import java.util.List;

public interface SubscriptionRepo extends PagingAndSortingRepository<UserSubscriptions, Long> {

    UserSubscriptions findOneByChannelAndSubscriber(Long channelId, Long subscriberId);

    List<UserSubscriptions> findAllByChannel(Long channelId);

    List<UserSubscriptions> findAllBySubscriber(Long userId);

    @Query("SELECT new sashaVosu.firstWebApplication.domain.dto.RecommendSubscribe(us.channel, count(us.channel)) " +
            "FROM UserSubscriptions us " +
            "WHERE us.subscriber IN :recommend " +
            "AND us.channel <> :id " +
            "AND us.channel NOT IN :recommend " +
            "GROUP BY us.channel ")
    Page<RecommendSubscribe> findSubsRecommend(@Param("id") Long id,
                                               @Param("recommend") List<Long> subscriptionsList,
                                               Pageable pageable);
}
