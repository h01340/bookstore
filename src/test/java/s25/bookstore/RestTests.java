package s25.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
//voidaan käyttää htpp-metodeja get, put, post, delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//status, content jne.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import s25.bookstore.model.Book;

@SpringBootTest
@AutoConfigureMockMvc
public class RestTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Get all books
    @Test
    public void testGetBooksWithoutAuth() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
    }

    // POST is working only for ADMIN
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void testPostBookAsAdmin() throws Exception {
        Book book = new Book("Testien Testi", "Minna", 2025);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = { "USER" })
    public void testPostBookAsUser() throws Exception {
        Book book = new Book("Testien Testi", "Minna", 2025);
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isForbidden());
    }

    // DELETE just ADMIN can do this
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void testDeleteBookAsAdmin() throws Exception {
        mockMvc.perform(delete("/books/1"));
        mockMvc.perform(get("/books/1"))
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assertEquals("null", content);
                });
    }

    @Test
    @WithMockUser(username = "user", authorities = { "USER" })
    public void testDeleteBookAsUser() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isForbidden());
    }

    /*
     * // PUT just ADMIN can do this
     * 
     * @Test
     * 
     * @WithMockUser(username = "admin", authorities = { "ADMIN" })
     * public void testGetAndUpdatBookAsAdmin() throws Exception {
     * // 1. Haetaan kirja id=1
     * mockMvc.perform(get("/books/1")
     * .accept(MediaType.APPLICATION_JSON))
     * .andExpect(status().isOk())
     * .andExpect(jsonPath("$.id").value(1))
     * .andExpect(jsonPath("$.title").exists())
     * .andExpect(jsonPath("$.author").exists());
     * 
     * // 2. Päivitetään kirjan tiedot
     * String updatedBookJson = """
     * {
     * "id": 1,
     * "title": "Updated Title",
     * "author": "Updated Author",
     * "year": 2025
     * }
     * """;
     * 
     * mockMvc.perform(put("/books/1")
     * .contentType(MediaType.APPLICATION_JSON)
     * .content(updatedBookJson))
     * .andExpect(status().isOk())
     * .andExpect(jsonPath("$.id").value(1))
     * .andExpect(jsonPath("$.title").value("Updated Title"))
     * .andExpect(jsonPath("$.author").value("Updated Author"))
     * .andExpect(jsonPath("$.year").value(2025));
     * }
     */
    @Test
    @WithMockUser(username = "user", authorities = { "USER" })
    public void testUpdateBookAsUser() throws Exception {
        Book updatedBook = new Book("Puttien putti", "Pellikka", 2022);
        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isForbidden());
    }

    // Testicase 6: GET /books/{id}
    @Test
    public void testGetBookById() throws Exception {
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookByInvalidId() throws Exception {
        mockMvc.perform(get("/books/9999"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assertEquals("null", content);
                });
    }
}
