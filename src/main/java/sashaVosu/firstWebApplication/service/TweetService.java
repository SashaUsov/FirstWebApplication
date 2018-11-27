package sashaVosu.firstWebApplication.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.domain.HashTag;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.exception.NotAllowedLengthOfTextException;
import sashaVosu.firstWebApplication.repo.HashTagRepo;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;
import sashaVosu.firstWebApplication.utils.TagUtil;
import sashaVosu.firstWebApplication.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TweetService {

    private final TweetRepo tweetRepo;

    private final UserTweetLikesRepo userTweetLikesRepo;

    private final HashTagRepo hashTagRepo;

    public TweetService(TweetRepo tweetRepo,
                        UserTweetLikesRepo userTweetLikesRepo, HashTagRepo hashTagRepo)
    {
        this.tweetRepo = tweetRepo;
        this.userTweetLikesRepo = userTweetLikesRepo;
        this.hashTagRepo = hashTagRepo;
    }

    @Value("${pic.path}")
    public String picPath;

//return list of all tweets with like count and user like status as to tweet
    public List<TweetModel> getTweetsList() {

        List<Tweet> tweetList = tweetRepo.findAll();

        return tweetList.stream()
                .map(Utils::convert).collect(Collectors.toList());

    }

//get list of all tweets by specific user id. With data about likes
    public List<TweetModel> getListOfMyTweet(String nickName) {

        return tweetRepo.findAllByCreator(nickName).stream()
                .map(Utils::convert)
                .collect(Collectors.toList());
    }

//Tweets shared by current user
    public List<TweetModel> myReTweetList(List<TweetModel> myTweets) {

        return myTweets.stream().filter(TweetModel::isReTweet).collect(Collectors.toList());

    }

//return TweetModel to the user after creating new tweet
    public TweetModel tweetCreate(CreateTweetModel tweetModel,
                                  String nickName
    ) {

        Tweet tweet = createTweet(tweetModel, nickName);

        return TweetConverters.toModel(tweet);


    }

//delete one tweet by tweet id from TWEET  and USER-LIKE-TWEET table
    public void del(Long id, String nickName) {

        Tweet tweet = tweetRepo.findOneById(id);

        if (tweet.isReTweet()) {
            userTweetLikesRepo.deleteAllByTweetId(id);

            Tweet firstTweet = tweet.getFirstTweet();
            firstTweet.getWhoReTweet().remove(tweet);
            tweetRepo.save(firstTweet);

            tweetRepo.deleteByIdAndCreator(id, nickName);

        } else {

            userTweetLikesRepo.deleteAllByTweetId(id);

            tweetRepo.deleteAllByFirstTweet(tweet);

            tweetRepo.deleteByIdAndCreator(id, nickName);
        }

    }

    public TweetModel getOne (Long id) {

        return Utils.convert(tweetRepo.findOneById(id));
    }

//update tweet by tweet id
    public TweetModel update(CreateTweetModel model,
                             String currentPrincipalName,
                             Long id
    ) {
        Tweet toUpdate = tweetRepo.findOneByCreatorAndId(currentPrincipalName, id);

        toUpdate.setText(model.getTweetText());

        return Utils.convert(tweetRepo.save(toUpdate));
    }

//create new tweet
    private Tweet createTweet(CreateTweetModel tweetModel,
                              String nickName
    ) {


        if (tweetModel.getTweetText().length() >= 1
                && tweetModel.getTweetText().length() <= 140)
        {

            Tweet newTweet = TweetConverters.toEntity(tweetModel, nickName);

            newTweet.setReTweet(false);

            tweetRepo.save(newTweet);

            Set<String> tagInTweet = TagUtil.tagDetector(newTweet.getText());

            if (tagInTweet.size() != 0) {

                addTagParam(newTweet, tagInTweet);
            }

            return newTweet;


        } else {

            throw new NotAllowedLengthOfTextException("Not allowed length of tweet");
        }
    }

//add hashTags to tweet
    private void addTagParam(Tweet tweet, Set<String> tagInTweet) {

        Set<String> finalSet = TagUtil.getTag(tagInTweet);

        for (String tag : finalSet) {

            String findTag = tag.toLowerCase();

            if (findTag.length() > 0) {

                if (hashTagRepo.findOneByTag(findTag) == null) {

                    HashTag hashTag = new HashTag();

                    hashTag.setTag(findTag);
                    hashTagRepo.save(hashTag);

                    tweet.getListTagsInTweet().add(hashTag);

                } else {
                    HashTag hashTagFromDb = hashTagRepo.findOneByTag(findTag);

                    tweet.getListTagsInTweet().add(hashTagFromDb);
                }
            }
        }
        tweetRepo.save(tweet);
    }

//Get the name of the picture added by the user to the tweet
    public String addTweetPic (MultipartFile file) throws IOException {

        if (file != null) {

            File uploadDir = new File(picPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(picPath + "/" + resultFileName));

            return resultFileName;
        }
        return null;
    }

//get tweet list by tag
    public List<TweetModel> getTweetListByTag(String tag) {
        String hashTag = tag.toLowerCase();

        HashTag hashTagFromDb = hashTagRepo.findOneByTag(tag);

        return hashTagFromDb.getTweetWithTagList().stream()
                .map(Utils::convert)
                .collect(Collectors.toList());
    }
}