package com.bookBazaar.models.converter;

import com.bookBazaar.models.dto.BookDTO;
import com.bookBazaar.models.entities.BookEntity;

public interface ModelConverterINT {
    BookEntity convertBookDTOToBookEntity(BookDTO bookDTO);
    BookDTO convertBookEntityToBookDTO(BookEntity bookEntity);
}
