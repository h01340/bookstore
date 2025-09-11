package s25.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import s25.bookstore.model.Book;
import s25.bookstore.model.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	/*
	 * @Bean
	 * public CommandLineRunner demo(BookRepository bookRepository) {
	 * return (args) -> {
	 * // lisätään pari kirjaa
	 * Book book1 = new Book("Opi Spring Boottia", "Pelle Hermanni");
	 * Book book2 = new Book("Opi Spring Boottia, osa 2", "Pelle Hermanni");
	 * // tallenna kirjat (h2)-tietokantaan
	 * bookRepository.save(book1);
	 * bookRepository.save(book2);
	 * 
	 * for (Book book : bookRepository.findAll()) {
	 * log.info(book.toString());
	 * }
	 * };
	 * }
	 */
}
