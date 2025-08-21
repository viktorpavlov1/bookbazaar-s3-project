package com.bookBazaar.models.dto;

import lombok.*;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookDTO {
    private Optional<Long> id;
    private String title;
    private String author;
    private String category;
    private double price;
    private int year;
    private String description;
    private int quantity;
}


