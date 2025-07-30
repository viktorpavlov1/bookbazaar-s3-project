package com.bookBazaar.services.interfaces;

import com.bookBazaar.models.dto.BookDTO;

import java.util.List;

public interface BookServiceINT {
    public BookDTO createNewBook(BookDTO newBookDetails);
    public List<BookDTO> getAllBooks();
    public BookDTO getSpecificBookByID(String id);
}