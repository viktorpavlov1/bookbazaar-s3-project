package com.bookBazaar.models.dto;

import lombok.*;

import java.util.Date;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {
    private Long id;
    private String sender;
    private String responder; // Can be null
    private String title;
    private String author;
    private Integer year; // Can be null
    private String comment; // Can be null
    private String response; // Can be null
    private String status; // Can be null
    private Date requestDate; // Can be null
    private Date responseDate; // Can be null
}