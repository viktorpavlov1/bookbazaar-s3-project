package com.bookBazaar.models.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Long id;
    private String username;
    private Date purchased_date;
    private double total_price;
    private List<PurchasedItemDTO> items;
}