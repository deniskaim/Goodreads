package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.TestBooksSetFactory;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CompositeSimilarityCalculatorTest {

    private static CompositeSimilarityCalculator calculator;
    private static final Book book1 = TestBooksSetFactory.createBookID1();
    private static final Book book2 = TestBooksSetFactory.createBookID2();

    private static SimilarityCalculator setUpCalculatorMock(double wantedSimilarity) {
        SimilarityCalculator calculatorMock = Mockito.mock(SimilarityCalculator.class);
        when(calculatorMock.calculateSimilarity(book1, book2)).thenReturn(wantedSimilarity);
        return calculatorMock;
    }

    @BeforeAll
    static void setUp() {

        SimilarityCalculator calculatorMock1 = setUpCalculatorMock(0.25);
        SimilarityCalculator calculatorMock2 = setUpCalculatorMock(0.5);

        Map<SimilarityCalculator, Double> similarityCalculatorMap = new HashMap<>();
        similarityCalculatorMap.put(calculatorMock1, 2d);
        similarityCalculatorMap.put(calculatorMock2, 1d);

        calculator = new CompositeSimilarityCalculator(similarityCalculatorMap);

    }

    @Test
    void testCalculateSimilarityNullFirst() {
        Book book = TestBooksSetFactory.createBookID1();
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(null, book),
            "calculateSimilarity() should throw an IllegalArgumentException when book is null");
    }

    @Test
    void testCalculateSimilarityNullSecond() {
        Book book = TestBooksSetFactory.createBookID1();
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(book, null),
            "calculateSimilarity() should throw an IllegalArgumentException when book is null");
    }

    @Test
    void testCalculateSimilarity() {

        double expectedSimilarity = 1d;
        double actualSimilarity = calculator.calculateSimilarity(book1, book2);
        double delta = 0.00001;
        assertEquals(expectedSimilarity, actualSimilarity, delta,
            "CompositeSimilarityCalculator doesn't calculate the correct similarity");
    }
}
