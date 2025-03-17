package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BookRecommender implements BookRecommenderAPI {

    private final Set<Book> books;
    private final SimilarityCalculator similarityCalculator;

    public BookRecommender(Set<Book> books, SimilarityCalculator similarityCalculator) {
        this.books = books;
        this.similarityCalculator = similarityCalculator;
    }

    private Comparator<Book> similarityCalculator(Map<Book, Double> bookSimilarityMap) {
        return (Book first, Book second) -> {
            int result = Double.compare(bookSimilarityMap.get(second), bookSimilarityMap.get(first));
            if (result != 0) {
                return result;
            }
            return first.ID().compareTo(second.ID());
        };
    }

    public SortedMap<Book, Double> recommendBooks(Book originBook, int maxN) {
        if (originBook == null) {
            throw new IllegalArgumentException("originBook is null");
        }
        if (maxN <= 0) {
            throw new IllegalArgumentException("Invalid maxN");
        }

        // First of all, we calculate the similarity of every book in the bookSet with the originBook
        Map<Book, Double> bookSimilarityMap = books.stream()
            .collect(Collectors.toMap(
                book -> book,
                book -> similarityCalculator.calculateSimilarity(book, originBook)
            ));

        // sort the books according to their similarity with the originBook
        SortedMap<Book, Double> sortedBooksMap = new TreeMap<>(similarityCalculator(bookSimilarityMap));
        sortedBooksMap.putAll(bookSimilarityMap);

        // get the first N key-value pairs from the sorted map
        return sortedBooksMap.entrySet()
            .stream()
            .limit(maxN)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (entry1, _) -> entry1,
                () -> new TreeMap<>(similarityCalculator(bookSimilarityMap))
            ));
    }

}
