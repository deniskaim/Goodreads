package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.TestBooksSetFactory;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookRecommenderTest {

    private static BookRecommender bookRecommender;
    private static SimilarityCalculator mockCalculator;

    @BeforeAll
    static void setUp() {
        Set<Book> books = Set.of(TestBooksSetFactory.createBookID1(), TestBooksSetFactory.createBookID16());
        mockCalculator = Mockito.mock(SimilarityCalculator.class);

        when(mockCalculator.calculateSimilarity(any(Book.class), any(Book.class))).thenReturn(0.5);

        bookRecommender = new BookRecommender(books, mockCalculator);
    }

    @Test
    void testRecommendBooksNullBook() {
        assertThrows(IllegalArgumentException.class, () -> bookRecommender.recommendBooks(null, 1),
            "recommendBooks() should throw an IllegalArgumentException when book is null");
    }

    @Test
    void testRecommendBooksInvalidN() {
        Book book = TestBooksSetFactory.createBookID1();
        assertThrows(IllegalArgumentException.class, () -> bookRecommender.recommendBooks(book, -1),
            "recommendBooks() should throw an IllegalArgumentException when N is less than or equal to 0");
    }

    @Test
    void testRecommendBooksOriginBookInBookSet() {
        Book originBook = TestBooksSetFactory.createBookID2();
        Book expectedTopSimilarBook = TestBooksSetFactory.createBookID16();

        when(mockCalculator.calculateSimilarity(expectedTopSimilarBook, originBook)).thenReturn(0.9);
        when(mockCalculator.calculateSimilarity(TestBooksSetFactory.createBookID1(), originBook)).thenReturn(0.7);

        SortedMap<Book, Double> actualResult = bookRecommender.recommendBooks(originBook, 2);

        assertEquals(expectedTopSimilarBook, actualResult.firstKey(),
            "recommendBooks() doesn't return the correct top recommendation");
        assertEquals(2, actualResult.size(),
            "The sorted map should contain 2 key-value pairs");
    }
}
