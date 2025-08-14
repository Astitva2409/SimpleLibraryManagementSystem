package com.astitva.librarysystem.A.Simple.Library.Management.System.dto;

import com.astitva.librarysystem.A.Simple.Library.Management.System.utils.GenderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Long id;

    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be blank")
    @Size(min = 2, max = 50, message = "the name should be in range (2, 50) characters")
    private String name;

    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be blank")
    @Min(value = 18, message = "Age must be greater than or equal to 18")
    private Integer age;
    private GenderType gender;
//    private List<Long> bookIds; // List of book IDs written by this author
}
