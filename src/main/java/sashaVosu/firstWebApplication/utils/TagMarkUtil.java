package sashaVosu.firstWebApplication.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TagMarkUtil {

//Searches for all words in the string containing #
    public static Set<String> tagDetector(String input) {

        return Arrays.stream(input.split(" "))
                .filter(s -> s.contains("#"))
                .collect(Collectors.toSet());
    }

//Cuts out # of words and returns tags
    public static Set<String> takeClearWord(Set<String> tagSet) {

        Set<String> finalSet = new HashSet<>();

        for (String str : tagSet){

            String string = Arrays.stream(str.split(""))
                    .skip(1)
                    .collect(Collectors.joining());

            finalSet.add(string);
        }

        return finalSet;
    }

    //Searches for all words in the string containing #
    public static Set<String> userMarkDetector(String input) {

        return Arrays.stream(input.split(" "))
                .filter(s -> s.contains("@"))
                .collect(Collectors.toSet());
    }
    
}
