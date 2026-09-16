package com.example.capstone1.Mudel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {


    @NotEmpty(message = "Enter your id please")
    private String id;

    @NotEmpty(message = "Enter your name please ^_^")
    @Size(min = 4)
    private String name;

    @NotNull(message ="Enter the product price please " )
    @Positive(message = "jest Allow positive number for price")
    private double price;


    @NotEmpty(message = "Enter your category please")
    private String categoryId;
}
