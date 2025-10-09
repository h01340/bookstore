package s25.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Book's title cannot be empty.")
    @Size(min = 1, max = 250)
    private String title;

    @NotEmpty(message = "Book's author cannot be empty.")
    @Size(min = 1, max = 250)
    private String author;

    @Min(value = 0, message = "Publishing year cannot be negative or null")
    @Column(name = "publishing_year")
    private Integer yearOfPublish;

    @ManyToOne
    @JoinColumn(name = "category_id") // categoryid on tietokannassa oleva fk
    private Category kategoria;

    // constructors for different purposes
    public Book() {
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book(String title, String author, int yearOfPublish) {
        this.title = title;
        this.author = author;
        this.yearOfPublish = yearOfPublish;
    }

    public Book(@NotEmpty(message = "Book's title cannot be empty.") @Size(min = 1, max = 250) String title,
            @NotEmpty(message = "Book's author cannot be empty.") @Size(min = 1, max = 250) String author,
            Category kategoria) {
        this.title = title;
        this.author = author;
        this.kategoria = kategoria;
    }

    public Book(@NotEmpty(message = "Book's title cannot be empty.") @Size(min = 1, max = 250) String title,
            @NotEmpty(message = "Book's author cannot be empty.") @Size(min = 1, max = 250) String author,
            @Min(value = 0, message = "Publishing year cannot be negative or null") Integer yearOfPublish,
            Category kategoria) {
        this.title = title;
        this.author = author;
        this.yearOfPublish = yearOfPublish;
        this.kategoria = kategoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(Integer yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public Category getKategoria() {
        return kategoria;
    }

    public void setKategoria(Category kategoria) {
        this.kategoria = kategoria;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", yearOfPublish=" + yearOfPublish
                + ", kategoria=" + kategoria + "]";
    }

}
