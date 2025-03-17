package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.io.Reader;
import java.io.StringReader;

public class TestTokenizerFactory {

    public static TextTokenizer createSampleTokenizer() {
        Reader reader = new StringReader("are\nabout\narent\nbecause\nhimself\nherself\n");
        return new TextTokenizer(reader);
    }
}
