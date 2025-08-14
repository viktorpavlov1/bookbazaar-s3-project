package com.bookBazaar.models.dto;

import lombok.*;


import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Optional<Long> id;
    private String name;
    private String username;
    private String password;
    private String role;
    private String address;
    private String email;
    private String phone;
    private String birthdate;
    private String abbreviation;
    private String favouriteBook;
}
