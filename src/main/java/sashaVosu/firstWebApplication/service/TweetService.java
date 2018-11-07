package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.exception.NotAllowedLengthOfTextException;
import sashaVosu.firstWebApplication.repo.TweetRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService {

    private final TweetRepo tweetRepo;

    public TweetService(TweetRepo tweetRepo) {
        this.tweetRepo = tweetRepo;
    }

    public List<TweetModel> getTweetsList() {

        return tweetRepo.findAll().stream().map(TweetConverters::toModel).collect(Collectors.toList());
    }

    public TweetModel tweetCreate(CreateTweetModel tweetModel, String nickName){

            if (tweetModel.getTweetText().length() >= 1 && tweetModel.getTweetText().length() <= 140) {

                Tweet newTweet = TweetConverters.toEntity(tweetModel, nickName);

                return TweetConverters.toModel(tweetRepo.save(newTweet));

            } else {

                throw new NotAllowedLengthOfTextException("Not allowed length of tweet");
            }
    }

    @Transactional
    public void del(Long id, String nickName) {

            tweetRepo.deleteByIdAndCreator(id, nickName);

    }

    public TweetModel update(CreateTweetModel model,
                             String currentPrincipalName, Long id)
    {
        Tweet toUpdate = tweetRepo.findOneByCreatorAndId(currentPrincipalName, id);

        toUpdate.setText(model.getTweetText());

        return TweetConverters.toModel(tweetRepo.save(toUpdate));
    }

    public TweetModel getOne(Long id) {

        return TweetConverters.toModel(tweetRepo.findOneById(id));
    }
}
