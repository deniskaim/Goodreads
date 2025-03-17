package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.BookTokenizer;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookFinder implements BookFinderAPI {

    private final Set<Book> books;
    private final BookTokenizer tokenizer;

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = new BookTokenizer(tokenizer);
    }

    @Override
    public Set<Book> allBooks() {
        return books;
    }

    @Override
    public Set<String> allGenres() {
        return books.stream()
            .map(Book::genres)
            .flatMap(List::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        if (authorName == null || authorName.isBlank()) {
            throw new IllegalArgumentException("Invalid authorName");
        }

        return books.stream()
            .filter(book -> book.author().equals(authorName))
            .toList();
    }

    private Predicate<Book> getGenrePredicate(Set<String> genres, MatchOption option) {

        return switch (option) {
            case MATCH_ALL -> book -> tokenizer.getGenresLowerCase(book).containsAll(genres);

            case MATCH_ANY -> book -> genres.stream()
                .anyMatch(genre -> tokenizer.getGenresLowerCase(book).contains(genre));
        };
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        if (genres == null) {
            throw new IllegalArgumentException("genres is null in searchByGenres()");
        }
        if (option == null) {
            throw new IllegalArgumentException("option is null in searchByGenres()");
        }

        return books.stream()
            .filter(getGenrePredicate(genres, option))
            .toList();
    }

    private Predicate<Book> getKeywordPredicate(Set<String> keywords, MatchOption option) {

        return switch (option) {
            case MATCH_ALL ->
                book -> keywords.stream().allMatch(keyword -> tokenizer.tokenizeBookDescription(book).contains(keyword)
                    || tokenizer.tokenizeBookTitle(book).contains(keyword));

            case MATCH_ANY ->
                book -> keywords.stream().anyMatch(keyword -> tokenizer.tokenizeBookDescription(book).contains(keyword)
                    || tokenizer.tokenizeBookTitle(book).contains(keyword));
        };
    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        if (keywords == null) {
            throw new IllegalArgumentException("keywords is null in searchByKeywords()");
        }
        if (option == null) {
            throw new IllegalArgumentException("option is null in searchByKeywords()");
        }

        return books.stream()
            .filter(getKeywordPredicate(keywords, option))
            .toList();
    }
}