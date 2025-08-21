package com.bookBazaar.models.repositories;

import com.bookBazaar.models.entities.PurchasedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedItemRepository extends JpaRepository<PurchasedItemEntity, Long> {

}