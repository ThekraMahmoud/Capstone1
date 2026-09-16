package com.example.capstone1.Mudel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {


    @NotEmpty(message = "Enter your id please")
    private String id ;

    // تاخذ البردوكت
    @NotEmpty(message = "Enter the prudent id please ")
    private String productid;

    //تاخذ المرشنت
    @NotEmpty(message = "Enter the merchant Id please ")
    private String merchantId;


    @NotNull(message = "Enter stock number please ")
    @Min(value = 10,message = "the stock most be at least 10")
    private int stock;
}
