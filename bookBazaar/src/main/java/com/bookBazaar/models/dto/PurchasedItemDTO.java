package com.bookBazaar.models.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchasedItemDTO {
    private Long id;
    private String title;
    private int purchased_qty;
    private double single_price;
}