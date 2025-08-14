package com.bookBazaar.models.other;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    private String subject;
    private Long id;
    private String role;
}