package s25.bookstore.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import s25.bookstore.model.Book;
import s25.bookstore.model.BookRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service is itself annotated with @Component.
 * This means any class marked with @Service is automatically
 * detected during component scanning and registered as a Spring bean
 * in the application context.
 */
@Service
public class InitDB {

    private static final Logger log = LoggerFactory.getLogger(InitDB.class);

    private final BookRepository bookRepository;

    public InitDB(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String insertDemoData() {
        try {
            bookRepository.save(new Book("DemoKirja1", "DemoKirjailija1", 2023));
            bookRepository.save(new Book("DemoKirja2", "DemoKirjailija1", 2024));
            bookRepository.save(new Book("DemoKirja3", "DemoKirjailija1", 2024));
            bookRepository.save(new Book("DemoKirja1", "DemoKirjailija4", 2025));
            return "some books inserted";
        } catch (DataAccessException e) {
            log.error("Database error: " + e.getMessage());
            return "Error: cannot save data to db";
        } catch (Exception e) {
            // yleinen varmistus yllättäville virheille
            log.error("Unexpected error: " + e.getMessage());
            return "error: unexpected save failure";
        }
    }

}
