package sashaVosu.firstWebApplication.utils;

import sashaVosu.firstWebApplication.domain.dto.RecommendSubscribe;

import java.util.Comparator;

public class RecommendComparatorUtil implements Comparator<RecommendSubscribe> {


    @Override
    public int compare(RecommendSubscribe o1, RecommendSubscribe o2) {
        return o2.getMutualSubs().compareTo(o1.getMutualSubs());
    }
}
