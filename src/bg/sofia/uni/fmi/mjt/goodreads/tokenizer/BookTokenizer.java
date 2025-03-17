package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
BookTokenizer uses a TextTokenizer to tokenize fields of a Book object
 */

public class BookTokenizer {

    private final TextTokenizer tokenizer;

    public BookTokenizer(TextTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public List<String> tokenizeBookDescription(Book book) {
        return tokenizer.tokenize(book.description());
    }

    public List<String> tokenizeBookTitle(Book book) {
        return tokenizer.tokenize(book.title());
    }

    /*
     * getGenresLowerCase is used in the BookFinder class, since
     * the tokenizer would convert "Mystery Thriller" in ["mystery, "thriller"] and
     * we would simply want "mystery thriller"
     */
    public Set<String> getGenresLowerCase(Book book) {
        return book.genres().stream()
            .map(String::toLowerCase)
            .collect(Collectors.toSet());
    }
}
