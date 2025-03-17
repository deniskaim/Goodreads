package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTokenizerTest {

    private TextTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        Reader reader = new StringReader("about\nare\nhavent\nthe\na\nof\nin");
        tokenizer = new TextTokenizer(reader);
    }

    @Test
    void testTokenizeShouldRemoveHyphen() {
        String input = "  @!@#   user-friendly !about. are!'@   haven't!          long-term, o'clock   ";
        List<String> expectedResult = List.of("userfriendly", "longterm", "oclock");
        List<String> actualResult = tokenizer.tokenize(input);
        assertEquals(expectedResult, actualResult, "tokenize() doesn't return the correct list of strings");
    }

    @Test
    void testTokenize2ShouldConcatenate() {
        String input = " The,a of in. ";
        List<String> expectedResult = List.of("thea");
        List<String> actualResult = tokenizer.tokenize(input);
        assertEquals(expectedResult, actualResult, "tokenize() doesn't return the correct list of strings");
    }


}
