package utility;

import java.util.List;
import java.util.stream.Stream;

public class StringOperations {

    public static List<String> getSubstringsFromString(String listOfTokens) {
        listOfTokens = listOfTokens.substring(1, listOfTokens.length() - 1);

        return Stream.of(listOfTokens.split(", "))
            .map(substring -> !substring.isEmpty() ? substring.substring(1, substring.length() - 1) : substring)
            .toList();
    }
}
