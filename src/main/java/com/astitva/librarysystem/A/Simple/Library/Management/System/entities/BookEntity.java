package com.astitva.librarysystem.A.Simple.Library.Management.System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDate publishedDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
}
