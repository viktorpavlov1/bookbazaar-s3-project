package com.bookBazaar.models.repositories;

import com.bookBazaar.models.entities.BookRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRequestRepository extends JpaRepository<BookRequestEntity, Long> {
    List<BookRequestEntity> findAllBySenderIgnoreCase(String username);
    List<BookRequestEntity> findAllByResponderIgnoreCase(String username);
}