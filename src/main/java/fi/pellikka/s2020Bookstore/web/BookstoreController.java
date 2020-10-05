package fi.pellikka.s2020Bookstore.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.pellikka.s2020Bookstore.domain.Book;
import fi.pellikka.s2020Bookstore.domain.BookRepository;
import fi.pellikka.s2020Bookstore.domain.CategoryRepository;

@Controller
public class BookstoreController {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/login")
	public String giveLoginPage() {
		return "login";
	}

	//@GetMapping("/allBooks")
	@GetMapping("/booklist")
	public String returnAllBooks(Model model) {
		model.addAttribute("books", bookRepository.findAll());
		return "booklist";
	}
	

	@GetMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addNewBook(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("categories", categoryRepository.findAll());
		return "addBook";
	}

	@PostMapping("/save")
	public String saveBook(Book book) {
		bookRepository.save(book);
		return "redirect:booklist";

	}

//	@PostMapping("/save")
//	public String saveBook(Book book, Model model) {
//		bookRepository.save(book);
//		model.addAttribute("books", bookRepository.findAll());
//		return "booklist";
//		
//	}
	
    //What is missing?
	@GetMapping(value = "/delete/{id}")
	public String deleteBook(@PathVariable("id") Long bookId) {
		bookRepository.deleteById(bookId);
		return "redirect:../booklist";
	}
	
    //What is missing?
	@GetMapping(value = "/edit/{id}")
	public String editBook(@PathVariable("id") Long bookId, Model model) {
		model.addAttribute("book", bookRepository.findById(bookId));
		model.addAttribute("categories", categoryRepository.findAll());
		return "editBook";
	}

	//some rest functionality
	// RESTful service to get all books
    @RequestMapping(value="/books", method = RequestMethod.GET)
    public @ResponseBody List<Book> bookListRest() {	
        return (List<Book>) bookRepository.findAll();
    }    
}
