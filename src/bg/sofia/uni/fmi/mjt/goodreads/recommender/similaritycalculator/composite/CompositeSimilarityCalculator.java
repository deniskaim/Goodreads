package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Map;

public class CompositeSimilarityCalculator implements SimilarityCalculator {

    private final Map<SimilarityCalculator, Double> similarityCalculatorMap;

    public CompositeSimilarityCalculator(Map<SimilarityCalculator, Double> similarityCalculatorMap) {
        if (similarityCalculatorMap == null) {
            throw new IllegalArgumentException("similarityCalculatorMap is null");
        }
        this.similarityCalculatorMap = similarityCalculatorMap;
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {

        if (first == null) {
            throw new IllegalArgumentException("first is null in calculateSimilarity()");
        }
        if (second == null) {
            throw new IllegalArgumentException("second is null in calculateSimilarity()");
        }

        return similarityCalculatorMap.entrySet()
            .stream()
            .mapToDouble(entry -> entry.getKey().calculateSimilarity(first, second) * entry.getValue())
            .sum();
    }

}