package sashaVosu.firstWebApplication.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class TagMarkUtil {

    //Searches for all words in the string containing #
    public static Set<String> tagDetector(String input) {

        return Arrays.stream(input.split(" "))
                .filter(s -> s.startsWith("#"))
                .collect(Collectors.toSet());
    }

    //Cuts out # of words and returns tags
    public static Set<String> takeClearWord(Set<String> tagSet) {

        return tagSet.stream().map(word -> word.substring(1))
                .collect(Collectors.toSet());
    }

    //Searches for all words in the string containing #
    public static Set<String> userMarkDetector(String input) {

        return Arrays.stream(input.split(" "))
                .filter(s -> s.startsWith("@"))
                .collect(Collectors.toSet());
    }


}
