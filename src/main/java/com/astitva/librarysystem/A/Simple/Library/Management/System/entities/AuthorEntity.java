package com.astitva.librarysystem.A.Simple.Library.Management.System.entities;

import com.astitva.librarysystem.A.Simple.Library.Management.System.utils.GenderType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authors")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer age;

    @Enumerated(value = EnumType.STRING)
    private GenderType gender;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<BookEntity> books;
}
