package com.bookBazaar.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="shoppingCart")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String product_name;
    private int qty;
}