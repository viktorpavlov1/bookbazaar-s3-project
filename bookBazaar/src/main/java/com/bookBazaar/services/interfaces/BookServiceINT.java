package com.bookBazaar.services.interfaces;

import com.bookBazaar.models.dto.BookDTO;

import java.util.List;

public interface BookServiceINT {
    public BookDTO createNewBook(BookDTO newBookDetails);
    public List<BookDTO> getAllBooks();
    public BookDTO getSpecificBookByID(String id);
    public List<BookDTO> getAllBooksByKind(String kind, String value);
    public void deleteSpecificBook(String id);
    public BookDTO updateSpecificBook(BookDTO updatedValues);
}
