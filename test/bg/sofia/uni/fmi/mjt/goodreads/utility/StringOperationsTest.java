package bg.sofia.uni.fmi.mjt.goodreads.utility;

import org.junit.jupiter.api.Test;
import utility.StringOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringOperationsTest {

    @Test
    void testGetSubstringsFromString() {

        String input =
            "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']";

        List<String> expectedResult =
            List.of("Classics", "Fiction", "Historical Fiction", "School", "Literature", "Young Adult", "Historical");

        List<String> actualResult = StringOperations.getSubstringsFromString(input);

        assertEquals(expectedResult, actualResult, "getSubstringsFromString() doesn't return the correct substrings");
    }

    @Test
    void testGetSubstringsFromStringEmptyInput() {

        String input = "['']";

        assertEquals(1, StringOperations.getSubstringsFromString(input).size(),
            "getSubstringsFromString() doesn't return the correct substrings");
    }
}
