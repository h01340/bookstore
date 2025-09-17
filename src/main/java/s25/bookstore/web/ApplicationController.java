package s25.bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import s25.bookstore.model.Book;
import s25.bookstore.model.BookRepository;
import s25.bookstore.model.CategoryRepository;
import s25.bookstore.service.InitDB;

@Controller
public class ApplicationController {

    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    // https://docs.spring.io/spring-boot/reference/using/spring-beans-and-dependency-injection.html
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final InitDB initDB;

    public ApplicationController(BookRepository bookRepository,
            CategoryRepository categoryRepository, InitDB initDB) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.initDB = initDB;

    }

    @GetMapping("/initDB")
    public String initializeDatabase() {
        log.info("CONTROLLER: initialize db");
        String ok = initDB.insertDemoData();
        log.info(ok);
        for (Book book : bookRepository.findAll()) {
            log.info(book.toString());
        }
        return "redirect:/main";
    }
}
