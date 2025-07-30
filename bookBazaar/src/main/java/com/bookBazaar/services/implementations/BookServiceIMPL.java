package com.bookBazaar.services.implementations;

import com.bookBazaar.models.converter.ModelConverterINT;
import com.bookBazaar.models.dto.BookDTO;
import com.bookBazaar.models.entities.BookEntity;
import com.bookBazaar.models.repositories.BookRepository;
import com.bookBazaar.services.interfaces.BookServiceINT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceIMPL implements BookServiceINT {

    private final BookRepository bookRepository;
    private final ModelConverterINT modelConverter;

    @Override
    public BookDTO createNewBook(BookDTO newBookDetails) {

        BookEntity preparedBook = modelConverter.convertBookDTOToBookEntity(newBookDetails);
        BookEntity savedBook = bookRepository.save(preparedBook);
        return modelConverter.convertBookEntityToBookDTO(savedBook);

    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<BookEntity> allBooksEntities = bookRepository.findAll();
        List<BookDTO> allBooks = new ArrayList<>();

        for(BookEntity specificBookEntity:allBooksEntities)
        {
            BookDTO specificBook = modelConverter.convertBookEntityToBookDTO(specificBookEntity);
            allBooks.add(specificBook);
        }
        return allBooks;
    }

    @Override
    public BookDTO getSpecificBookByID(String id) {
        Long idConverted = Long.parseLong(id);
        Optional<BookEntity> optionalBook = bookRepository.findById(idConverted);
        BookEntity preparedBook = optionalBook.orElseThrow(() -> new RuntimeException("Book not found"));
        return modelConverter.convertBookEntityToBookDTO(preparedBook);
    }

    @Override
    public List<BookDTO> getAllBooksByKind(String kind, String value) {
        switch(kind)
        {
            case "title":
                List<BookEntity> allBookEntitiesWithSpecificTitle = bookRepository.findAllByTitleContainingIgnoreCase(value);
                List<BookDTO> allBookWithSpecificTitle = new ArrayList<>();

                for(BookEntity specificBookEntity:allBookEntitiesWithSpecificTitle)
                {
                    BookDTO specificBook = modelConverter.convertBookEntityToBookDTO(specificBookEntity);
                    allBookWithSpecificTitle.add(specificBook);
                }
                return allBookWithSpecificTitle;


            case "author":
                List<BookEntity> allBookEntitiesWithSpecificAuthor = bookRepository.findAllByAuthorContainingIgnoreCase(value);
                List<BookDTO> allBookWithSpecificAuthor = new ArrayList<>();

                for(BookEntity specificBookEntity:allBookEntitiesWithSpecificAuthor)
                {
                    BookDTO specificBook = modelConverter.convertBookEntityToBookDTO(specificBookEntity);
                    allBookWithSpecificAuthor.add(specificBook);
                }
                return allBookWithSpecificAuthor;


            case "category":
                List<BookEntity> allBookEntitiesWithSpecificCategory = bookRepository.findAllByCategoryContainingIgnoreCase(value);
                List<BookDTO> allBookWithSpecificCategory = new ArrayList<>();

                for(BookEntity specificBookEntity:allBookEntitiesWithSpecificCategory)
                {
                    BookDTO specificBook = modelConverter.convertBookEntityToBookDTO(specificBookEntity);
                    allBookWithSpecificCategory.add(specificBook);
                }
                return allBookWithSpecificCategory;

            default:
                throw new RuntimeException("Unsupported kind for search");
        }
    }


}