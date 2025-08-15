package com.astitva.librarysystem.A.Simple.Library.Management.System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;

    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be blank")
    @Size(min = 2, max = 50, message = "the name should be in range (2, 50) characters")
    private String title;

    private LocalDate publishedDate;

    @NotNull(message = "Author id cannot be null. Please enter a valid author id")
    private Long authorId;
}
