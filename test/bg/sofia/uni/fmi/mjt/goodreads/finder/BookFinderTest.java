package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.TestBooksSetFactory;
import bg.sofia.uni.fmi.mjt.goodreads.TestTokenizerFactory;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookFinderTest {

    private static BookFinder bookFinder;

    @BeforeAll
    static void setUp() {
        Set<Book> books = TestBooksSetFactory.createSampleBooks();
        TextTokenizer tokenizer = TestTokenizerFactory.createSampleTokenizer();
        bookFinder = new BookFinder(books, tokenizer);
    }

    @Test
    void testAllGenres() {
        Set<String> expectedGenres =
            Set.of("Fantasy", "Fiction", "Young Adult", "Magic", "Childrens", "Middle Grade", "Classics", "Romance",
                "Historical Fiction", "Literature", "Historical", "Audiobook", "Contemporary", "Novels");

        Set<String> actualGenres = bookFinder.allGenres();

        assertEquals(expectedGenres, actualGenres, "allGenres() doesn't return the correct set of genres");
    }

    @Test
    void testSearchByAuthorNullName() {

        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByAuthor(null),
            "searchByAuthor() should throw an IllegalArgumentException when author is null");
    }

    @Test
    void testSearchByAuthorEmptyName() {

        String emptyName = "";
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByAuthor(emptyName),
            "searchByAuthor() should throw an IllegalArgumentException when author is null");
    }

    @Test
    void testSearchByAuthor() {
        String name = "Jane Austen";

        List<Book> result = bookFinder.searchByAuthor(name);

        assertEquals(1, result.size(),
            "searchByAuthor() should return a List<Book> with size == 1, but the returned size is " + result.size());
    }

    @Test
    void testSearchByAuthorInvalidName() {
        String name = "random fake name";

        List<Book> result = bookFinder.searchByAuthor(name);
        assertTrue(result.isEmpty(),
            "searchByAuthor() should return an empty List<Book> when the author's name is fake");

    }

    @Test
    void testSearchByGenresNullGenres() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByGenres(null, MatchOption.MATCH_ALL),
            "searchByGenres() should throw an IllegalArgumentException when genres is null");
    }

    @Test
    void testSearchByGenresMatchAll() {

        Set<String> genresInput = Set.of("classics", "historical", "fiction");
        List<Book> actualBooks = bookFinder.searchByGenres(genresInput, MatchOption.MATCH_ALL);

        assertEquals(2, actualBooks.size(),
            "two books contain \"classics\", \"historical\", \"fiction\" instead of " + actualBooks.size());
    }

    @Test
    void testSearchByGenresMatchAny() {
        Set<String> genresInput = Set.of("classics", "historical", "fiction");
        List<Book> actualBooks = bookFinder.searchByGenres(genresInput, MatchOption.MATCH_ANY);

        final int expectedBooksCount = 3;
        assertEquals(expectedBooksCount, actualBooks.size(),
            "all three books contain at least one of [\"classics\", \"historical\", \"fiction\"] instead of " +
                actualBooks.size());

    }

    @Test
    void testSearchByGenresTwoWordGenre() {
        Set<String> genresInput = Set.of("historical fiction");
        List<Book> actualBooks = bookFinder.searchByGenres(genresInput, MatchOption.MATCH_ALL);

        assertEquals(actualBooks.size(), 2,
            "two books contain \"historical fiction\" instead of " + actualBooks.size());
    }

    @Test
    void testSearchByKeywordsNullKeywords() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByKeywords(null, MatchOption.MATCH_ALL),
            "searchByKeywords() should throw an IllegalArgumentException when keywords is null");
    }

    @Test
    void testSearchByKeywordsMatchAllShouldReturnEmptyList() {

        Set<String> keywordsInput = Set.of("harry", "pride");
        List<Book> actualBooks = bookFinder.searchByKeywords(keywordsInput, MatchOption.MATCH_ALL);

        assertEquals(actualBooks.size(), 0,
            "no books contain both of the keywords \"harry\" and \"pride\" in their description / title");

    }

    @Test
    void testSearchByKeywordsMatchAny() {
        Set<String> keywordsInput = Set.of("harry", "pride");
        List<Book> actualBooks = bookFinder.searchByKeywords(keywordsInput, MatchOption.MATCH_ANY);

        assertEquals(actualBooks.size(), 2,
            "two books contain at least one of the keywords \"harry\" and \"pride\" in their description / title");

        List<String> actualIDs = actualBooks.stream().map(Book::ID).toList();
        List<String> expectedIDs = List.of("1", "2");
        assertEquals(actualIDs, expectedIDs,
            "searchByKeywords() does not find the correct books");
    }

    @Test
    void testSearchByKeywordsMatchAll() {
        Set<String> keywordsInput = Set.of("in", "the");
        List<Book> actualBooks = bookFinder.searchByKeywords(keywordsInput, MatchOption.MATCH_ALL);

        assertEquals(actualBooks.size(), 2,
            "two books contain the keywords \"in\" and \"the\" in their description / title");

    }

}
