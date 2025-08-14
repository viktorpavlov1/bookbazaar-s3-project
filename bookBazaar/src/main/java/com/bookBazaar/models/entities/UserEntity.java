package com.bookBazaar.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
