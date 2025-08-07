package com.bookBazaar.models.converter;

import com.bookBazaar.models.dto.BookDTO;
import com.bookBazaar.models.entities.BookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ModelConverter {

    public BookEntity convertBookDTOToBookEntity(BookDTO bookDTO) {
        return BookEntity.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .category(bookDTO.getCategory())
                .price(bookDTO.getPrice())
                .year(bookDTO.getYear())
                .description(bookDTO.getDescription())
                .quantity(bookDTO.getQuantity())
                .build();
    }

    public BookDTO convertBookEntityToBookDTO(BookEntity bookEntity) {
        return BookDTO.builder()
                .id(Optional.of(bookEntity.getId()))
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .category(bookEntity.getCategory())
                .price(bookEntity.getPrice())
                .year(bookEntity.getYear())
                .description(bookEntity.getDescription())
                .quantity(bookEntity.getQuantity())
                .build();
    }
}