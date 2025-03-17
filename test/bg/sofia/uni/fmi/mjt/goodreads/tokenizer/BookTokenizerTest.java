package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import bg.sofia.uni.fmi.mjt.goodreads.TestBooksSetFactory;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTokenizerTest {

    private BookTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        Reader reader = new StringReader("about\nare\nhavent");
        TextTokenizer textTokenizer = new TextTokenizer(reader);
        tokenizer = new BookTokenizer(textTokenizer);
    }

    @Test
    void testTokenizeBookDescription() {

        Book book = TestBooksSetFactory.createBookID1();

        List<String> expectedResult =
            List.of("harry", "potter", "thinks", "he", "is", "an", "ordinary", "boy", "until", "he", "is", "rescued",
                "by", "an", "owl", "taken", "to", "hogwarts", "school", "of", "witchcraft", "and", "wizardry", "learns",
                "to", "play", "quidditch", "and", "does", "battle", "in", "a", "deadly", "duel", "the", "reason",
                "harry", "potter", "is", "a", "wizard");

        List<String> actualResult = tokenizer.tokenizeBookDescription(book);

        assertEquals(expectedResult, actualResult,
            "tokenizeBookDescription() doesn't return the correct list of strings");
    }

    @Test
    void testTokenizeBookTitle() {

        Book book = TestBooksSetFactory.createBookID1();
        List<String> expectedResult =
            List.of("harry", "potter", "and", "the", "philosopher’s", "stone", "harry", "potter", "1");
        /* the ’ in philosopher’s is used in the actual csv file and
         * the regex doesn't detect it since ' and ’ are different apostrophes
         */

        List<String> actualResult = tokenizer.tokenizeBookTitle(book);
        assertEquals(expectedResult, actualResult,
            "tokenizeBookTitle() doesn't return the correct list of strings");
    }
}
