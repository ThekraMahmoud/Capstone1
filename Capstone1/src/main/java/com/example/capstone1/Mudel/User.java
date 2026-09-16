package com.example.capstone1.Mudel;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {


    @NotEmpty(message = "you forget id , please Enter your id !")
    private String id;

    @NotEmpty(message = "ops ! You are not Enter your name ")
    @Size(min = 6,message = "name length most be more than 5 ")
    private String userName;

    @NotEmpty(message = "to use the system most be enter password ^_^ ")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Please Enter strong password")
    private String Password;

    @NotEmpty(message = "pleas enter your email")
    @Email(message =  "you forget @ ; valid your email please ")
    private String email;

    @NotEmpty(message = "Enter yor role please ^_^")
    @Pattern(regexp = ("^(Admin|Customer)$"),message = "The role most be one of this Admin or Customer ")
    private String role;

    @NotNull(message = "Enter your balance please")
    @Positive(message = "jest allow positive number")
    private double balance;


}
