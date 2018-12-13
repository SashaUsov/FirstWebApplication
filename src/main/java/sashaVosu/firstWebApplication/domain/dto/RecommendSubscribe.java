package sashaVosu.firstWebApplication.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendSubscribe {

    public RecommendSubscribe(Long id, Long mutualSubs) {
        this.id = id;
        this.mutualSubs = mutualSubs;
    }

    private Long id;

    private Long mutualSubs;
}
