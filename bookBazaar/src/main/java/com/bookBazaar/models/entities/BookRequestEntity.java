package com.bookBazaar.models.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="book_request")
public class BookRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;

    @Column(nullable = true)
    private String responder;
    private String title;
    private String author;

    @Column(nullable = true)
    private Integer year;

    @Column(nullable = true)
    private String comment;

    @Column(nullable = true)
    private String response;

    @Column(nullable = true, columnDefinition = "VARCHAR(20) DEFAULT 'pending'")
    private String status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = "pending";
        }
    }
    @Column(nullable = true)
    private Date requestDate;
    @Column(nullable = true)
    private Date responseDate;

}