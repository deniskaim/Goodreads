package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.TestBooksSetFactory;
import bg.sofia.uni.fmi.mjt.goodreads.TestTokenizerFactory;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TFIDFSimilarityCalculatorTest {

    private static TFIDFSimilarityCalculator calculator;

    @BeforeAll
    static void setUp() {
        Set<Book> books = TestBooksSetFactory.createSampleBooks();
        TextTokenizer tokenizer = TestTokenizerFactory.createSampleTokenizer();
        calculator = new TFIDFSimilarityCalculator(books, tokenizer);
    }

    private Book createBook() {
        // the tokenizer removes "about" before the calculation
        return Book.of(new String[] {"ID", "title", "author", "a, the, pride, pride, about", "[]", "1", "1", "url"});
    }

    @Test
    void testComputeTFNullBook() {
        assertThrows(IllegalArgumentException.class, () -> calculator.computeTF(null),
            "computeTF() should throw an IllegalArgumentException when book is null");
    }

    @Test
    void testComputeTF() {
        Book book = createBook();

        Map<String, Double> expectedResult = Map.of("a", 0.25, "the", 0.25, "pride", 0.5);
        Map<String, Double> actualResult = calculator.computeTF(book);

        assertEquals(expectedResult, actualResult,
            "computeTF() doesn't return the correct map of word and its ratio");
    }

    @Test
    void testComputeIDFNullBook() {
        assertThrows(IllegalArgumentException.class, () -> calculator.computeIDF(null),
            "computeIDF() should throw an IllegalArgumentException when book is null");
    }

    @Test
    void testComputeIDF() {
        Book book = createBook();

        Map<String, Double> expectedResult =
            Map.of("a", Math.log10((double) 3 / 2), "the", Math.log10(1), "pride", Math.log10(3));
        Map<String, Double> actualResult = calculator.computeIDF(book);

        assertEquals(expectedResult, actualResult, "computeIDF() doesn't return the correct map");
    }

    @Test
    void testComputeTFIDFNullBook() {
        assertThrows(IllegalArgumentException.class, () -> calculator.computeTFIDF(null),
            "computeTFIDF() should throw an IllegalArgumentException when book is null");
    }

    @Test
    void testComputeTFIDF() {
        Book book = createBook();

        final double ratio1TF = 0.25;
        final double ratio2TF = 0.25;
        final double ratio3TF = 0.5;

        final double ratio1IDF = (double) 3 / 2;
        final double ratio2IDF = (double) 3 / 3;
        final double ratio3IDF = 3;
        Map<String, Double> expectedResult =
            Map.of("a", ratio1TF * Math.log10(ratio1IDF), "the", ratio2TF * Math.log10(ratio2IDF), "pride",
                ratio3TF * Math.log10(ratio3IDF));

        Map<String, Double> actualResult = calculator.computeTFIDF(book);
        assertEquals(expectedResult, actualResult, "computeTFIDF() doesn't return the correct map");
    }

    @Test
    void testCalculateSimilarityFirstNull() {
        Book book = createBook();
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(null, book),
            "calculateSimilarity() should throw an IllegalArgumentException when first book is null");
    }

    @Test
    void testCalculateSimilaritySecondNull() {
        Book book = createBook();
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(book, null),
            "calculateSimilarity() should throw an IllegalArgumentException when second book is null");
    }

    @Test
    void testCalculateSimilaritySameBook() {
        Book first = TestBooksSetFactory.createBookID1();

        final double expectedSimilarity = 1;
        double actualSimilarity = calculator.calculateSimilarity(first, first);
        final double delta = 0.00001;

        assertEquals(expectedSimilarity, actualSimilarity, delta,
            "calculateSimilarity() should return 1 since it calculates the similarity of a book with itself");

    }

    @Test
    void testCalculateSimilarityUpperBound() {

        Book first = TestBooksSetFactory.createBookID1();
        Book second = TestBooksSetFactory.createBookID2();
        double actualSimilarity = calculator.calculateSimilarity(first, second);

        assertTrue(Double.compare(actualSimilarity, 0d) <= 1,
            "calculateSimilarity() should return similarity <= 1 which is the maximum similarity");
    }
}
