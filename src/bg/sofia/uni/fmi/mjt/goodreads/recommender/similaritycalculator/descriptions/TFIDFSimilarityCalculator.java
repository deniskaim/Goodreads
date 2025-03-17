package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.BookTokenizer;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TFIDFSimilarityCalculator implements SimilarityCalculator {

    private final Set<Book> books;
    private final BookTokenizer bookTokenizer;

    private final Map<String, Integer> wordBooksCountMap;

    private Map<String, Integer> createBookCountMap() {
        /*
         * Saving in a map how many books contain a certain word from a description of the books
         */

        Map<String, Integer> wordBooksCountMap = new HashMap<>();
        for (Book currentBook : books) {

            Set<String> uniqueWordsBookDescription =
                new HashSet<>(bookTokenizer.tokenizeBookDescription(currentBook));

            for (String word : uniqueWordsBookDescription) {
                wordBooksCountMap.put(word, wordBooksCountMap.getOrDefault(word, 0) + 1);
            }
        }
        return wordBooksCountMap;
    }

    public TFIDFSimilarityCalculator(Set<Book> books, TextTokenizer tokenizer) {
        if (books == null) {
            throw new IllegalArgumentException("books is null");
        }
        if (tokenizer == null) {
            throw new IllegalArgumentException("tokenizer is null");
        }
        this.books = books;
        this.bookTokenizer = new BookTokenizer(tokenizer);
        wordBooksCountMap = createBookCountMap();
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {
        if (first == null) {
            throw new IllegalArgumentException("first is null in calculateSimilarity()");
        }
        if (second == null) {
            throw new IllegalArgumentException("second is null in calculateSimilarity()");
        }

        Map<String, Double> tfIdfScoresFirst = computeTFIDF(first);
        Map<String, Double> tfIdfScoresSecond = computeTFIDF(second);

        return cosineSimilarity(tfIdfScoresFirst, tfIdfScoresSecond);
    }

    public Map<String, Double> computeTFIDF(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book is null in computeTFIDF()");
        }

        Map<String, Double> idfMap = computeIDF(book);
        Map<String, Double> tfMap = computeTF(book);

        return tfMap.keySet().stream()
            .filter(wordBooksCountMap::containsKey)
            .collect(Collectors.toMap(
                word -> word,
                word -> idfMap.get(word) * tfMap.get(word)
            ));
    }

    public Map<String, Double> computeTF(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book is null in computeTF()");
        }

        List<String> tokenizedDescription = bookTokenizer.tokenizeBookDescription(book);
        int wordCount = tokenizedDescription.size();

        return tokenizedDescription.stream()
            .collect(Collectors.toMap(
                word -> word,
                _ -> (1d / wordCount),
                Double::sum
            ));
    }

    public Map<String, Double> computeIDF(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book is null in computeIDF()");
        }

        int booksCount = books.size();

        /* tokenize the description of book and
         * include only words which can be found in the wordBooksCountMap
         */

        Set<String> tokenizedDescription =
            bookTokenizer.tokenizeBookDescription(book).stream()
                .filter(wordBooksCountMap::containsKey)
                .collect(Collectors.toSet());

        // calculate the ratio for every word in the tokenized description of book
        return tokenizedDescription.stream()
            .collect(Collectors.toMap(
                word -> word,
                word -> Math.log10((double) booksCount / wordBooksCountMap.get(word))
            ));
    }

    private double cosineSimilarity(Map<String, Double> first, Map<String, Double> second) {
        double magnitudeFirst = magnitude(first.values());
        double magnitudeSecond = magnitude(second.values());

        return dotProduct(first, second) / (magnitudeFirst * magnitudeSecond);
    }

    private double dotProduct(Map<String, Double> first, Map<String, Double> second) {
        Set<String> commonKeys = new HashSet<>(first.keySet());
        commonKeys.retainAll(second.keySet());

        return commonKeys.stream()
            .mapToDouble(word -> first.get(word) * second.get(word))
            .sum();
    }

    private double magnitude(Collection<Double> input) {
        double squaredMagnitude = input.stream()
            .map(v -> v * v)
            .reduce(0.0, Double::sum);

        return Math.sqrt(squaredMagnitude);
    }
}