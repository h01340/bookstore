package s25.bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
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
import s25.bookstore.model.CategoryRepository;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    // https://docs.spring.io/spring-boot/reference/using/spring-beans-and-dependency-injection.html
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookController(BookRepository bookRepository,
            CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;

    }

    @GetMapping(value = { "main", "/" })
    public String showMainPage() {
        log.info("showMainPage...");
        return "main";
    }

    @GetMapping("/booklist")
    public String getAllBooks(Model model) {
        log.info("getAllBooks...");
        model.addAttribute("kirjat", bookRepository.findAll());
        return "booklist";
    }

    // insert new book, first open addBook html page
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add")
    public String openAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute(("categories"), categoryRepository.findAll());
        return "addBook";
    }

    // save a new book
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveBook")
    public String savebook(Book book) {
        log.info("CONTROLLER: Save book: " + book);
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    // poista booklist-sivulta valittu kirja
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable Long id) {
        log.info("Delete book which id = " + id);
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }

    // editoi booklist-sivulla valittua kirjaa (huom. ettei id:tä voi muokata)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        log.info("Edit book which id = " + id);
        model.addAttribute("editBook", bookRepository.findById(id));
        model.addAttribute(("categories"), categoryRepository.findAll());

        return "editBookWithValidation";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveEditedBook")
    public String saveEditedBook(@Valid @ModelAttribute("editBook") Book book,
            BindingResult bindingResult, Model model) {
        log.info("CONTROLLER: Save edited the book - check validation of book: " + book);

        if (bindingResult.hasErrors()) {
            log.error("some validation error happened, book: " + book);
            model.addAttribute("editBook", book);
            model.addAttribute("categories", categoryRepository.findAll());
            return "editBookWithValidation";
        }
        log.info("tallenna kirja: " + book);
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    /*
     * @PostMapping("/saveEditedBook")
     * public String saveEditedBook(Book book) {
     * log.info("CONTROLLER: Save edited the book " + book);
     * bookRepository.save(book);
     * return "redirect:/booklist";
     * }
     */

}
