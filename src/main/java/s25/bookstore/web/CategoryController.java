package s25.bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import s25.bookstore.model.Category;
import s25.bookstore.model.CategoryRepository;

@Controller
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @GetMapping("/categorylist")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categorylist";
    }

    @GetMapping("/addCategory")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "addCategory";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(Category category) {
        log.info("save category " + category);
        categoryRepository.save(category);
        return "redirect:/categorylist";

    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Long id) {
        log.info("delete category " + id);
        categoryRepository.deleteById(id);
        return "redirect:/categorylist";

    }

}
