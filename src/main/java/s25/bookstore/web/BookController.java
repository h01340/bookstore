package s25.bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import s25.bookstore.model.Book;
import s25.bookstore.model.BookRepository;
import s25.bookstore.service.InitDB;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    // https://docs.spring.io/spring-boot/reference/using/spring-beans-and-dependency-injection.html
    private final BookRepository bookRepository;
    private final InitDB initDB;

    public BookController(BookRepository bookRepository, InitDB initDB) {
        this.bookRepository = bookRepository;
        this.initDB = initDB;
    }

    @GetMapping(value = { "main", "/" })
    public String showMainPage() {
        log.info("showMainPage...");
        return "main";
    }

    // insert some demo data to database
    @GetMapping("/initDB")
    public String initializeDatabase() {
        log.info("CONTROLLER: initialize db");
        String ok = initDB.insertDemoData();
        log.info(ok);
        for (Book book : bookRepository.findAll()) {
            log.info(book.toString());
        }
        return "redirect:/booklist";
    }

    @GetMapping("/booklist")
    public String getAllBooks(Model model) {
        log.info("getAllBooks...");
        model.addAttribute("kirjat", bookRepository.findAll());
        return "booklist";
    }

    // insert new book, first open addBook html page
    @GetMapping("/add")
    public String openAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "addBook";
    }

    // save new book
    @PostMapping("/saveBook")
    public String savebook(Book book) {
        log.info("CONTROLLER: Save book: " + book);
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    // poista booklist-sivulta valittu kirja
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable Long id) {
        log.info("Delete book which id = " + id);
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }

    // editoi booklist-sivulla valittua kirjaa (huom. ettei id:tä voi muokata)
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        log.info("Edit book which id = " + id);
        model.addAttribute("editBook", bookRepository.findById(id));

        return "editBookWithValidation";
    }

    /*
     * @PostMapping("/saveEditedBook")
     * public String saveEditedBook(Book book) {
     * log.info("CONTROLLER: Save edited the book " + book);
     * bookRepository.save(book);
     * return "redirect:/booklist";
     * }
     */

    @PostMapping("/saveEditedBook")
    public String saveEditedBook(@Valid @ModelAttribute("editBook") Book book,
            BindingResult bindingResult, Model model) {
        log.info("CONTROLLER: Save edited the book - check validation of book: " +
                book);
        if (bindingResult.hasErrors()) {
            log.error("some validation error happened, book: " + book);
            model.addAttribute("editBook", book);

            return "editBookWithValidation";
        }
        log.info("tallenna kirja: " + book);
        bookRepository.save(book);
        return "redirect:/booklist";
    }

}
