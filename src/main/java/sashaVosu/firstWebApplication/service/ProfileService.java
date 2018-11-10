package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.repo.TweetRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final TweetRepo tweetRepo;

    public ProfileService(TweetRepo tweetRepo) {
        this.tweetRepo = tweetRepo;
    }

    public List<TweetModel> getListOfMyTweet(String currentPrincipalName) {

        return tweetRepo.findAllByCreator(currentPrincipalName).stream()
                .map(TweetConverters::toModel).collect(Collectors.toList());
    }
}
