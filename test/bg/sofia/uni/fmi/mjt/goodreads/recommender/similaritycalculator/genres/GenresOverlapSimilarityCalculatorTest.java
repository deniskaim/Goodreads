package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.TestBooksSetFactory;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenresOverlapSimilarityCalculatorTest {

    private final GenresOverlapSimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

    @Test
    void testCalculateSimilarityNullFirstBook() {
        Book book = TestBooksSetFactory.createBookID1();
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(null, book),
            "calculateSimilarity() should throw an IllegalArgumentException when first book is null");
    }

    @Test
    void testCalculateSimilarityNullSecondBook() {

        Book book = TestBooksSetFactory.createBookID1();
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(book, null),
            "calculateSimilarity() should throw an IllegalArgumentException when second book is null");
    }

    @Test
    void testCalculateSimilarity() {
        Book first = TestBooksSetFactory.createBookID1();
        Book second = TestBooksSetFactory.createBookID2();

        final int expectedMinSize = 7;
        final int intersectionSize = 2;
        final double delta = 0.00001;

        double expectedSimilarity = (double) intersectionSize / expectedMinSize;
        assertEquals(expectedSimilarity, calculator.calculateSimilarity(first, second), delta,
            "calculateSimilarity() doesn't calculate the correct genre similarity of the two books");
    }

    @Test
    void testCalculateSimilaritySameBookShouldReturnOne() {
        Book first = TestBooksSetFactory.createBookID1();
        Book second = TestBooksSetFactory.createBookID1();

        final double expectedSimilarity = 1;
        final double delta = 0.00001;
        assertEquals(expectedSimilarity, calculator.calculateSimilarity(first, second), delta,
            "calculateSimilarity() should return 1 when calculating the similarity of a book with itself");
    }
}
