package s25.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import s25.bookstore.model.AppUser;
import s25.bookstore.model.AppUserRepository;
import s25.bookstore.model.Book;
import s25.bookstore.model.BookRepository;
import s25.bookstore.model.Category;
import s25.bookstore.model.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookRepository bookRepository,
			CategoryRepository categoryRepository,
			AppUserRepository userRepository) {
		return (args) -> {

			log.info("create application users");
			userRepository
					.save(new AppUser("user", "$2a$10$6Ehacq06EduOQsntdjKXteXkvuTHKhAWVyuIoHUyBevq5TUhv4nhG", "USER"));
			userRepository.save(
					new AppUser("admin", "$2a$10$kjjhzAwU3c4wap0cH0HLM.BUrLfBT6QbDw1YGzpwfmVkJ7RfoG/he", "ADMIN"));

			categoryRepository.save(new Category("Dokkari"));
			categoryRepository.save(new Category("Scifi"));

			// lisätään pari kirjaa
			Book book1 = new Book("Opi Spring Boottia", "Minna");
			Book book2 = new Book("Opi Spring Boottia, osa 2", "Pelle Hermanni");
			Book book3 = new Book("Opi Spring Boottia, osa 3", "Pelle Hermanni",
					categoryRepository.findByName("Scifi").get(0));

			// tallenna kirjat (h2)-tietokantaan
			bookRepository.save(book1);
			bookRepository.save(book2);
			bookRepository.save(book3);

			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}
		};
	}

}
