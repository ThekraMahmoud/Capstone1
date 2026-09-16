package com.example.capstone1.Mudel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "Enter your id please")
    private String id;

    @NotEmpty(message = "Enter your name please ^_^")
    @Size(min = 4)
    private String name;
}
