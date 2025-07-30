package com.bookBazaar.models.repositories;

import com.bookBazaar.models.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByTitleContainingIgnoreCase(String title);
    List<BookEntity> findAllByAuthorContainingIgnoreCase(String author);
    List<BookEntity> findAllByCategoryContainingIgnoreCase(String category);

}