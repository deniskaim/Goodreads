package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookLoaderTest {

    @Test
    void testLoad() {
        Reader reader = new StringReader("N,Book,Author,Description,Genres,Avg_Rating,Num_Ratings,URL\n" +
            "1,\"Harry Potter and the Philosopher’s Stone (Harry Potter, #1)\",J.K. Rowling,\"Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!\",\"['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']\",4.47,\"9,278,135\",https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone\n" +
            "2,Pride and Prejudice,Jane Austen,\"Since its immediate success in 1813, Pride and Prejudice has remained one of the most popular novels in the English language. Jane Austen called this brilliant work \"\"her own darling child\"\" and its vivacious heroine, Elizabeth Bennet, \"\"as delightful a creature as ever appeared in print.\"\" The romantic clash between the opinionated Elizabeth and her proud beau, Mr. Darcy, is a splendid performance of civilized sparring. And Jane Austen's radiant wit sparkles as her characters dance a delicate quadrille of flirtation and intrigue, making this book the most superb comedy of manners of Regency England.Alternate cover edition of ISBN 9780679783268\",\"['Classics', 'Fiction', 'Romance', 'Historical Fiction', 'Literature', 'Historical', 'Audiobook']\",4.28,\"3,944,155\",https://www.goodreads.com/book/show/1885.Pride_and_Prejudice\n" +
            "16,The Kite Runner,Khaled Hosseini,\"1970s Afghanistan: Twelve-year-old Amir is desperate to win the local kite-fighting tournament and his loyal friend Hassan promises to help him. But neither of the boys can foresee what would happen to Hassan that afternoon, an event that is to shatter their lives. After the Russians invade and the family is forced to flee to America, Amir realises that one day he must return to an Afghanistan under Taliban rule to find the one thing that his new world cannot grant him: redemption.\",\"['Fiction', 'Historical Fiction', 'Classics', 'Contemporary', 'Novels', 'Historical', 'Literature']\",4.33,\"2,954,721\",https://www.goodreads.com/book/show/77203.The_Kite_Runner");

        // The Book Factory creates a Set with books with ID 1, 2 and 16
        Set<Book> expectedBooks = TestBooksSetFactory.createSampleBooks();
        Set<Book> actualBooks = BookLoader.load(reader);

        assertEquals(expectedBooks, actualBooks,
            "load() doesn't return the correct set of books");
    }
}
