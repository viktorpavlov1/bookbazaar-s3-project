package com.bookBazaar.models.repositories;

import com.bookBazaar.models.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> getAllByUsernameIgnoreCase(String username);
    void deleteAllByUsernameIgnoreCase(String username);
}