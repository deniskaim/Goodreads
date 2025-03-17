package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.ArrayList;
import java.util.List;

public class GenresOverlapSimilarityCalculator implements SimilarityCalculator {

    @Override
    public double calculateSimilarity(Book first, Book second) {

        if (first == null) {
            throw new IllegalArgumentException("first cannot be null");
        }
        if (second == null) {
            throw new IllegalArgumentException("second cannot be null");
        }

        List<String> firstBookGenres = first.genres();
        List<String> secondBookGenres = second.genres();
        int minSize = Math.min(firstBookGenres.size(), secondBookGenres.size());

        List<String> intersection = new ArrayList<>(firstBookGenres);
        intersection.retainAll(secondBookGenres);
        return ((double) intersection.size()) / minSize;
    }
}