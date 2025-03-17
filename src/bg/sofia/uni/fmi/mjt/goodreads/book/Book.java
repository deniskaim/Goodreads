package bg.sofia.uni.fmi.mjt.goodreads.book;

import utility.StringOperations;

import java.util.List;

public record Book(
    String ID,
    String title,
    String author,
    String description,
    List<String> genres,
    double rating,
    int ratingCount,
    String URL) {

    private static final int ID_INDEX = 0;
    private static final int TITLE_INDEX = 1;
    private static final int AUTHOR_INDEX = 2;
    private static final int DESCRIPTION_INDEX = 3;
    private static final int GENRES_INDEX = 4;
    private static final int RATING_INDEX = 5;
    private static final int RATING_COUNT_INDEX = 6;
    private static final int URL_INDEX = 7;
    private static final String COMMA_SEPARATOR = ",";
    private static final String NO_SEPARATOR = "";

    public static Book of(String[] tokens) {

        return new Book(tokens[ID_INDEX], tokens[TITLE_INDEX], tokens[AUTHOR_INDEX],
            tokens[DESCRIPTION_INDEX], StringOperations.getSubstringsFromString(tokens[GENRES_INDEX]),
            Double.parseDouble(tokens[RATING_INDEX]),
            Integer.parseInt(tokens[RATING_COUNT_INDEX].replace(COMMA_SEPARATOR, NO_SEPARATOR)),
            tokens[URL_INDEX]);
    }
}
