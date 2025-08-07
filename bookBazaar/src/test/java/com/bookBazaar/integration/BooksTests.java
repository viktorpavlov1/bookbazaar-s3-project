package com.bookBazaar.integration;

import com.bookBazaar.controllers.BookController;
import com.bookBazaar.models.dto.BookDTO;
import com.bookBazaar.services.implementations.BookServiceIMPL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BooksTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookServiceIMPL bookServiceUseCases;

    //region Mocks
    private List<BookDTO> generateBookDTOList(){
        BookDTO book1 = BookDTO.builder()
                .title("Harry Potter and the Sorcerer's Stone")
                .author("J.K. Rowling")
                .category("Fantasy")
                .price(10.00)
                .year(1997)
                .description("First Harry Potter book")
                .quantity(5)
                .build();

        BookDTO book2 = BookDTO.builder()
                .title("Harry Potter and the Chamber of Secrets")
                .author("J.K. Rowling")
                .category("Fantasy")
                .price(10.00)
                .year(1998)
                .description("Second Harry Potter book")
                .quantity(8)
                .build();

        return List.of(book1, book2);
    }

    private BookDTO generateBookDTO(){

        return BookDTO.builder()
                .title("Harry Potter and the Sorcerer's Stone")
                .author("J.K. Rowling")
                .category("Fantasy")
                .price(10.00)
                .year(1997)
                .description("First Harry Potter book")
                .quantity(5)
                .build();
    }
    //endregion
    @Test
    void integration_getAllBooks_shouldReturn200Response() throws Exception {
        List<BookDTO> response = generateBookDTOList();

        when(bookServiceUseCases.getAllBooks()).thenReturn(response);

        mockMvc.perform(get("/api/books/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE));

        verify(bookServiceUseCases).getAllBooks();
    }

    @Test
    void integration_getSpecificBook_shouldReturn200Response() throws Exception {
        BookDTO desiredBook = generateBookDTO();
        Long id = Long.parseLong("1");
        desiredBook.setId(Optional.of(id));
        when(bookServiceUseCases.getSpecificBookByID("1")).thenReturn(desiredBook);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                                "id": 1,
                                "title": "Harry Potter and the Sorcerer's Stone",
                                "author": "J.K. Rowling",
                                "category": "Fantasy",
                                "price": 10.0,
                                "year": 1997,
                                "description": "First Harry Potter book",
                                "quantity": 5
                        }"""
                ));

    }

    @Test
    void integration_getByKind_shouldReturn200Response() throws Exception {
        List<BookDTO> response = generateBookDTOList();
        Long id = Long.parseLong("1");
        when(bookServiceUseCases.getAllBooksByKind("category", "Fantasy")).thenReturn(response);

        mockMvc.perform(get("/api/books/find/category")
                .contentType(APPLICATION_JSON_VALUE)
                .content("Fantasy"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE));

        verify(bookServiceUseCases).getAllBooksByKind("category", "Fantasy");
    }
}