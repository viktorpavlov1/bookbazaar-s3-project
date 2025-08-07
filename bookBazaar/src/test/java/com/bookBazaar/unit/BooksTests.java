package com.bookBazaar.unit;

import com.bookBazaar.models.converter.ModelConverter;
import com.bookBazaar.models.dto.BookDTO;
import com.bookBazaar.models.entities.BookEntity;
import com.bookBazaar.models.repositories.BookRepository;
import com.bookBazaar.services.implementations.BookServiceIMPL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class BooksTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceIMPL bookServiceUseCases;

    private final ModelConverter modelConverter = new ModelConverter();


    //region Mocks
    private List<BookEntity> generateBookEntityList(){
        BookEntity book1 = BookEntity.builder()
                .id(123L)
                .title("Harry Potter and the Sorcerer's Stone")
                .author("J.K. Rowling")
                .category("Fantasy")
                .price(10.00)
                .year(1997)
                .description("First Harry Potter book")
                .quantity(5)
                .build();

        BookEntity book2 = BookEntity.builder()
                .id(123L)
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
    private BookEntity generateBookEntity(){
        Long id = Long.parseLong("123");
        return BookEntity.builder()
                .id(id)
                .title("Harry Potter and the Sorcerer's Stone")
                .author("J.K. Rowling")
                .category("Fantasy")
                .price(10.00)
                .year(1997)
                .description("First Harry Potter book")
                .quantity(5)
                .build();
    }

    private BookEntity updatedBookEntity(){
        Long id = Long.parseLong("123");
        return BookEntity.builder()
                .id(id)
                .title("Harry Potter 1")
                .author("J.K. Rowling")
                .category("Fantasy")
                .price(10.00)
                .year(1997)
                .description("First Harry Potter book")
                .quantity(5)
                .build();
    }
    //endregion

    //region Tests
    @Test
    void Unit_bookServiceUseCases_getAllBooks_shouldReturnAllBooks() {
        //Expected result
        List<BookEntity> expectedAllBooksEntities = generateBookEntityList();
        List<BookDTO> expectedResult = new ArrayList<>();

        for(BookEntity specificBookEntity:expectedAllBooksEntities)
        {
            BookDTO specificBook = modelConverter.convertBookEntityToBookDTO(specificBookEntity);
            expectedResult.add(specificBook);
        }

        //Actual result
        when(bookRepository.findAll()).thenReturn(generateBookEntityList());
        List<BookDTO> actualResult = bookServiceUseCases.getAllBooks();

        //Assert
        assertEquals(expectedResult.size(), actualResult.size());
    }

    @Test
    void Unit_bookServiceUseCases_getSpecificBookByID_shouldReturnSpecificBook() {
        //Expected result
        BookEntity expectedBookEntity = generateBookEntity();
        BookDTO expectedResult = modelConverter.convertBookEntityToBookDTO(expectedBookEntity);

        //Actual result
        Long id = Long.parseLong("123");
        when(bookRepository.findById(id)).thenReturn(Optional.of(generateBookEntity()));
        BookDTO actualResult = bookServiceUseCases.getSpecificBookByID("123");

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void Unit_bookServiceUseCases_getAllBooksByKind_shouldReturnAllBooksBySpecificTitle() {
        //Expected result
        List<BookEntity> expectedBookEntities = generateBookEntityList();
        List<BookDTO> expectedResult = new ArrayList<>();

        for(BookEntity specificBookEntity:expectedBookEntities)
        {
            BookDTO specificBook = modelConverter.convertBookEntityToBookDTO(specificBookEntity);
            expectedResult.add(specificBook);
        }

        //Actual result
        when(bookRepository.findAllByTitleContainingIgnoreCase("Harry Potter")).thenReturn(generateBookEntityList());
        List<BookDTO> actualResult = bookServiceUseCases.getAllBooksByKind("title", "Harry Potter");

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void Unit_bookServiceUseCases_getAllBooksByKind_shouldReturnAllBooksBySpecificAuthor() {
        //Expected result
        List<BookEntity> expectedBookEntities = generateBookEntityList();
        List<BookDTO> expectedResult = new ArrayList<>();

        for(BookEntity specificBookEntity:expectedBookEntities)
        {
            BookDTO specificBook = modelConverter.convertBookEntityToBookDTO(specificBookEntity);
            expectedResult.add(specificBook);
        }

        //Actual result
        when(bookRepository.findAllByAuthorContainingIgnoreCase("J.K. Rowling")).thenReturn(generateBookEntityList());
        List<BookDTO> actualResult = bookServiceUseCases.getAllBooksByKind("author", "J.K. Rowling");

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void Unit_bookServiceUseCases_getAllBooksByKind_shouldReturnAllBooksBySpecificCategory() {
        //Expected result
        List<BookEntity> expectedBookEntities = generateBookEntityList();
        List<BookDTO> expectedResult = new ArrayList<>();

        for(BookEntity specificBookEntity:expectedBookEntities)
        {
            BookDTO specificBook = modelConverter.convertBookEntityToBookDTO(specificBookEntity);
            expectedResult.add(specificBook);
        }

        //Actual result
        when(bookRepository.findAllByCategoryContainingIgnoreCase("Fantasy")).thenReturn(generateBookEntityList());
        List<BookDTO> actualResult = bookServiceUseCases.getAllBooksByKind("category", "Fantasy");

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void Unit_bookServiceUseCases_getAllBooksByKind_shouldReturnException() {

        //Assert
        RuntimeException actualException = assertThrows(RuntimeException.class, () -> {
            bookServiceUseCases.getAllBooksByKind("year", "1997");
        }
        );
        assertEquals("Unsupported kind for search", actualException.getMessage());

    }

    @Test
    void Unit_bookServiceUseCases_deleteSpecificBook_shouldDeleteSpecificBook() {
        //Arrange
        Long id = Long.parseLong("123");
        when(bookRepository.findById(id))
                .thenReturn(Optional.of(generateBookEntity()))
                .thenReturn(Optional.empty());
        doNothing().when(bookRepository).deleteById(id);

        //Act and Assert
        bookServiceUseCases.deleteSpecificBook("123");
        verify(bookRepository).deleteById(id);
        verify(bookRepository, times(2)).findById(id);
    }

    @Test
    void Unit_bookServiceUseCases_updateSpecificBook_shouldUpdateDetailsOfSpecificBook() {
        //Expected result
        Long id = Long.parseLong("123");
        BookEntity updatedMockEntity = updatedBookEntity();
        BookDTO expectedResult = modelConverter.convertBookEntityToBookDTO(updatedMockEntity);
        when(bookRepository.findById(id)).thenReturn(Optional.of(generateBookEntity()));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(updatedBookEntity());


        //Actual result
        BookDTO actualResult = bookServiceUseCases.updateSpecificBook(modelConverter.convertBookEntityToBookDTO(updatedMockEntity));

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void Unit_bookServiceUseCases_createNewBook_shouldCreateNewBookSuccessfully() {
        //Expected result
        BookEntity newBookEntity = generateBookEntity();
        BookDTO newBookDto = modelConverter.convertBookEntityToBookDTO(newBookEntity);
        when(bookRepository.save(any(BookEntity.class))).thenReturn(newBookEntity);

        //Actual result
        BookDTO actualResult = bookServiceUseCases.createNewBook(newBookDto);

        //Assert
        assertEquals(newBookDto, actualResult);
    }

    //endregion
}