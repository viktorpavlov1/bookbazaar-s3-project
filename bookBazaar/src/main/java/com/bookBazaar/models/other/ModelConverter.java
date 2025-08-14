package com.bookBazaar.models.other;

import com.bookBazaar.models.dto.BookDTO;
import com.bookBazaar.models.dto.UserDTO;
import com.bookBazaar.models.entities.BookEntity;
import com.bookBazaar.models.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ModelConverter {

    public BookEntity convertBookDTOToBookEntity(BookDTO bookDTO) {
        return BookEntity.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .category(bookDTO.getCategory())
                .price(bookDTO.getPrice())
                .year(bookDTO.getYear())
                .description(bookDTO.getDescription())
                .quantity(bookDTO.getQuantity())
                .build();
    }

    public BookDTO convertBookEntityToBookDTO(BookEntity bookEntity) {
        return BookDTO.builder()
                .id(Optional.of(bookEntity.getId()))
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .category(bookEntity.getCategory())
                .price(bookEntity.getPrice())
                .year(bookEntity.getYear())
                .description(bookEntity.getDescription())
                .quantity(bookEntity.getQuantity())
                .build();
    }

    public UserEntity convertUserDTOToUserEntity(UserDTO userDTO, String encodedPassword) {
        return UserEntity.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(encodedPassword)
                .role(userDTO.getRole())
                .address(userDTO.getAddress())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .birthdate(userDTO.getBirthdate())
                .abbreviation(userDTO.getAbbreviation())
                .favouriteBook(userDTO.getFavouriteBook())
                .build();
    }

    public UserDTO convertUserEntityToUserDTO(UserEntity userEntity, String password){
        return UserDTO.builder()
                .id(Optional.ofNullable(userEntity.getId()))
                .name(userEntity.getName())
                .username(userEntity.getUsername())
                .password(password)
                .role(userEntity.getRole())
                .address(userEntity.getAddress())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .birthdate(userEntity.getBirthdate())
                .abbreviation(userEntity.getAbbreviation())
                .favouriteBook(userEntity.getFavouriteBook())
                .build();
    }
}
