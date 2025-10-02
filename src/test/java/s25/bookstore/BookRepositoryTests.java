package s25.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import s25.bookstore.model.Book;
import s25.bookstore.model.BookRepository;
import s25.bookstore.model.CategoryRepository;

@DataJpaTest
// @SpringBootTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// // if you are using real db
public class BookRepositoryTests {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void findByAuthorShouldReturnAuthor() {
        List<Book> books = bookRepository.findByAuthor("Minna");

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("Minna");
    }

    @Test
    public void createNewBook() {
        Book book = new Book("testien testi", "Minna");
        bookRepository.save(book);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void deleteBook() {
        List<Book> books = bookRepository.findByAuthor("Minna");
        Book book = books.get(0);
        bookRepository.delete(book);
        List<Book> newBooks = bookRepository.findByAuthor("Minna");
        assertThat(newBooks).hasSize(0);
    }

}
