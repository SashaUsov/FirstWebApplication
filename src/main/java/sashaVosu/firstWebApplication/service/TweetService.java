package sashaVosu.firstWebApplication.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sashaVosu.firstWebApplication.converters.TweetConverters;
import sashaVosu.firstWebApplication.converters.UserConverters;
import sashaVosu.firstWebApplication.domain.ApplicationUser;
import sashaVosu.firstWebApplication.domain.HashTag;
import sashaVosu.firstWebApplication.domain.Tweet;
import sashaVosu.firstWebApplication.domain.dto.CreateTweetModel;
import sashaVosu.firstWebApplication.domain.dto.TweetModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.exception.NotAllowedLengthOfTextException;
import sashaVosu.firstWebApplication.repo.HashTagRepo;
import sashaVosu.firstWebApplication.repo.TweetRepo;
import sashaVosu.firstWebApplication.repo.UserRepo;
import sashaVosu.firstWebApplication.repo.UserTweetLikesRepo;
import sashaVosu.firstWebApplication.utils.DeleteTweetUtil;
import sashaVosu.firstWebApplication.utils.TagMarkUtil;
import sashaVosu.firstWebApplication.utils.TweetReTweetUtil;

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

    private final UserRepo userRepo;

    public TweetService(TweetRepo tweetRepo,
                        UserTweetLikesRepo userTweetLikesRepo,
                        HashTagRepo hashTagRepo,
                        UserRepo userRepo
    ) {
        this.tweetRepo = tweetRepo;
        this.userTweetLikesRepo = userTweetLikesRepo;
        this.hashTagRepo = hashTagRepo;
        this.userRepo = userRepo;
    }

    @Value("${pic.path}")
    public String picPath;

    //return list of all tweets with like count and user like status as to tweet
    public List<TweetModel> getTweetsList(Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        Page<Tweet> tweetList = tweetRepo.findAllByPublished(true,
                new PageRequest(page, size, Sort.Direction.DESC,"creationData"));

        return tweetList.stream()
                .map(TweetReTweetUtil::convert).collect(Collectors.toList());

    }

    //get list of all tweets by specific user id. With data about likes
    public List<TweetModel> getListOfMyTweet(String nickName, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        return tweetRepo.findAllByCreatorAndPublished(nickName, true,
                new PageRequest(page, size, Sort.Direction.DESC,"creationData")).stream()
                .map(TweetReTweetUtil::convert)
                .collect(Collectors.toList());
    }

    //Tweets shared by current user
    public List<TweetModel> myReTweetList(List<TweetModel> myTweets) {

        return myTweets.stream().filter(TweetModel::isReTweet)
                .collect(Collectors.toList());

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

        Tweet tweet = tweetRepo.findOneByIdAndPublished(id, true);

        tweet.setPublished(false);

        tweetRepo.save(tweet);

        if (tweet.isReTweet()) {
            userTweetLikesRepo.deleteAllByTweetId(id);

            Tweet firstTweet = tweet.getFirstTweet();
            firstTweet.getWhoReTweet().remove(tweet);
            tweetRepo.save(firstTweet);

        } else {

            List<Tweet> deactTweet = tweetRepo.findAllByFirstTweet(tweet).stream()
                    .map(DeleteTweetUtil::deactivateTweet)
                    .collect(Collectors.toList());

            deactTweet.stream().map(tweetRepo::save);

        }

    }

    public TweetModel getOne(Long id) {

        return TweetReTweetUtil.convert(tweetRepo.findOneByIdAndPublished(id, true));
    }

    //update tweet by tweet id
    public TweetModel update(CreateTweetModel model,
                             String creator,
                             Long id
    ) {

        if (model.getTweetText().length() >= 1
                && model.getTweetText().length() <= 140) {

            Tweet toUpdate = tweetRepo.findOneByCreatorAndId(creator, id);

            toUpdate.setText(model.getTweetText());
            toUpdate.getListTagsInTweet().clear();
            toUpdate.getMarkUserList().clear();

            Set<String> tagInTweet = TagMarkUtil.tagDetector(model.getTweetText());

            if (tagInTweet.size() != 0) {

                addTagParam(toUpdate, tagInTweet);
            }

            Set<String> markInTweet = TagMarkUtil.userMarkDetector(toUpdate.getText());

            if (markInTweet.size() != 0) {

                addMarkParam(toUpdate, markInTweet);
            }

            return TweetReTweetUtil.convert(toUpdate);

        } else {

            throw new NotAllowedLengthOfTextException("Not allowed length of tweet");
        }
    }

    //create new tweet
    private Tweet createTweet(CreateTweetModel tweetModel,
                              String nickName
    ) {


        if (tweetModel.getTweetText().length() >= 1
                && tweetModel.getTweetText().length() <= 140) {

            Tweet newTweet = TweetConverters.toEntity(tweetModel, nickName);

            newTweet.setReTweet(false);

            newTweet.setPublished(true);

            tweetRepo.save(newTweet);

            Set<String> tagInTweet = TagMarkUtil.tagDetector(newTweet.getText());

            if (tagInTweet.size() != 0) {

                addTagParam(newTweet, tagInTweet);
            }

            Set<String> markInTweet = TagMarkUtil.userMarkDetector(newTweet.getText());

            if (markInTweet.size() != 0) {

                addMarkParam(newTweet, markInTweet);
            }

            return newTweet;


        } else {

            throw new NotAllowedLengthOfTextException("Not allowed length of tweet");
        }
    }

    //mark user in tweet
    private void addMarkParam(Tweet tweet, Set<String> markInTweet) {

        Set<String> finalSet = TagMarkUtil.takeClearWord(markInTweet);

        for (String mark : finalSet) {

            ApplicationUser user = userRepo.findOneByNickNameAndActive(mark, true);

            tweet.getMarkUserList().add(user);

        }
        tweetRepo.save(tweet);
    }

    //add hashTags to tweet
    private void addTagParam(Tweet tweet, Set<String> tagInTweet) {

        Set<String> finalSet = TagMarkUtil.takeClearWord(tagInTweet);

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
    public String addTweetPic(MultipartFile file) throws IOException {

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

        return hashTagFromDb.getTweetWithTagList().stream().filter(Tweet::isPublished)
                .map(TweetReTweetUtil::convert)
                .collect(Collectors.toList());
    }

    //get user list by mark in tweet
    public List<UserModel> getMarkUserList(Long tweetId) {

        Tweet oneTweetById = tweetRepo.findOneByIdAndPublished(tweetId, true);

        return oneTweetById.getMarkUserList().stream()
                .map(UserConverters::toModel)
                .collect(Collectors.toList());
    }
}