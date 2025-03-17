package bg.sofia.uni.fmi.mjt.goodreads.book;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    private String[] createTokens() {
        return new String[] {"1", "Harry Potter and the Philosopher’s Stone (Harry Potter, #1)", "J.K. Rowling",
            "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, " +
                "taken to Hogwarts School of Witchcraft and Wizardry," +
                " learns to play Quidditch and does battle in a deadly duel. " +
                "The Reason ... HARRY POTTER IS A WIZARD!",
            "['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']", "4.47",
            "9,278,135", "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone"
        };
    }

    @Test
    void testOf() {

        String[] tokens = createTokens();

        String expectedID = "1";
        String expectedTitle = "Harry Potter and the Philosopher’s Stone (Harry Potter, #1)";
        String expectedAuthor = "J.K. Rowling";
        String expectedDescription =
            "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, " +
                "taken to Hogwarts School of Witchcraft and Wizardry," +
                " learns to play Quidditch and does battle in a deadly duel. " +
                "The Reason ... HARRY POTTER IS A WIZARD!";
        List<String> expectedGenres =
            List.of("Fantasy", "Fiction", "Young Adult", "Magic", "Childrens", "Middle Grade", "Classics");

        final double expectedRating = 4.47;
        final int expectedRatingCount = 9278135;
        String expectedURL =
            "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone";

        Book book = Book.of(tokens);
        assertEquals(expectedID, book.ID(), "Book.of() doesn't create a valid Book ID");
        assertEquals(expectedDescription, book.description(), "Book.of() doesn't create a valid Book description");
        assertEquals(expectedGenres, book.genres(), "Book.of() doesn't create a valid Book genres");
        assertEquals(expectedAuthor, book.author(), "Book.of() doesn't create a valid Book author");
        assertEquals(expectedRating, book.rating(), "Book.of() doesn't create a valid Book rating");
        assertEquals(expectedRatingCount, book.ratingCount(), "Book.of() doesn't create a valid Book ratingCount");
        assertEquals(expectedTitle, book.title(), "Book.of() doesn't create a valid Book title");
        assertEquals(expectedURL, book.URL(), "Book.of() doesn't create a valid Book URL");

    }
}
