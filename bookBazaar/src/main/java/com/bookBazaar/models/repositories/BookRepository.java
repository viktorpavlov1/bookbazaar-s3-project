package com.bookBazaar.models.repositories;

import com.bookBazaar.models.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

}