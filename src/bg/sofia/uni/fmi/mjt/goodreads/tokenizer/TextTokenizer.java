package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextTokenizer {

    private static final String PUNCTUATION_REGEX = "\\p{Punct}";
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String SPACE_SEPARATOR = " ";
    private static final String NO_SEPARATOR = "";

    private final Set<String> stopwords;

    public TextTokenizer(Reader stopwordsReader) {
        try (var br = new BufferedReader(stopwordsReader)) {
            stopwords = br.lines().collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not load dataset", ex);
        }
    }

    public List<String> tokenize(String input) {

        input = input.replaceAll(PUNCTUATION_REGEX, NO_SEPARATOR);
        input = input.replaceAll(WHITESPACE_REGEX, SPACE_SEPARATOR);
        input = input.trim();

        String[] tokens = input.split(SPACE_SEPARATOR);

        return Stream.of(tokens)
            .map(String::toLowerCase)
            .filter(word -> !stopwords().contains(word))
            .toList();
    }

    public Set<String> stopwords() {
        return stopwords;
    }
}