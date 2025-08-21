package com.bookBazaar.models.other;

import com.bookBazaar.models.dto.*;
import com.bookBazaar.models.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Date;
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

    public CartItemEntity convertCartItemDTOToCartItemEntity(CartItemDTO cartItemDTO) {
        return CartItemEntity.builder()
                .username(cartItemDTO.getUsername())
                .product_name(cartItemDTO.getProduct_name())
                .qty(cartItemDTO.getQty())
                .build();
    }

    public CartItemDTO convertCartItemEntityToCartItemDTO(CartItemEntity cartItemEntity) {
        return CartItemDTO.builder()
                .id(Optional.ofNullable(cartItemEntity.getId()))
                .username(cartItemEntity.getUsername())
                .product_name(cartItemEntity.getProduct_name())
                .qty(cartItemEntity.getQty())
                .build();
    }

    public PurchasedItemDTO convertPurchasedItemEntityToPurchasedItemDTO(PurchasedItemEntity purchasedItemEntity)
    {
        return PurchasedItemDTO.builder()
                .id(purchasedItemEntity.getPurchase_id())
                .title(purchasedItemEntity.getTitle())
                .purchased_qty(purchasedItemEntity.getPurchased_qty())
                .single_price(purchasedItemEntity.getSingle_price())
                .build();
    }

    public BookRequestEntity convertBookRequestDTOToBookRequestEntity(BookRequestDTO bookRequestDTO)
    {
        return BookRequestEntity.builder()
                .sender(bookRequestDTO.getSender())
                .responder(bookRequestDTO.getResponder())
                .title(bookRequestDTO.getTitle())
                .author(bookRequestDTO.getAuthor())
                .year(bookRequestDTO.getYear())
                .comment(bookRequestDTO.getComment())
                .response(bookRequestDTO.getResponse())
                .status(bookRequestDTO.getStatus())
                .requestDate(bookRequestDTO.getRequestDate())
                .responseDate(bookRequestDTO.getResponseDate())
                .build();
    }
    public BookRequestDTO convertBookRequestEntityToBookRequestDTO(BookRequestEntity bookRequestEntity)
    {
        return BookRequestDTO.builder()
                .id(bookRequestEntity.getId())
                .sender(bookRequestEntity.getSender())
                .responder(bookRequestEntity.getResponder())
                .title(bookRequestEntity.getTitle())
                .author(bookRequestEntity.getAuthor())
                .year(bookRequestEntity.getYear())
                .comment(bookRequestEntity.getComment())
                .response(bookRequestEntity.getResponse())
                .status(bookRequestEntity.getStatus())
                .requestDate(bookRequestEntity.getRequestDate())
                .responseDate(bookRequestEntity.getResponseDate())
                .build();
    }
}