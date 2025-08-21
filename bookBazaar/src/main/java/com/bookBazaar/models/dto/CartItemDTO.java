package com.bookBazaar.models.dto;

import lombok.*;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CartItemDTO {
    private Optional<Long> id;
    private String username;
    private String product_name;
    private int qty;
}