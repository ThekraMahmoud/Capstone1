package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Mudel.MerchantStock;
import com.example.capstone1.Mudel.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/get")
    public ResponseEntity<?> get(){
        ArrayList<Product> merchantStocks=productService.get();
        return ResponseEntity.status(200).body(merchantStocks);
    }


    @PostMapping("/add")
    public ResponseEntity<?>add(@RequestBody @Valid Product product , Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        String check = productService.add(product);

        return switch (check) {case "category id not found" -> ResponseEntity.status(400).body(new ApiResponse("Category ID not found"));

            case "product id already use" ->ResponseEntity.status(400).body(new ApiResponse("Product ID already used"));

            case "true" -> ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));

            default -> ResponseEntity.status(400) .body(new ApiResponse("Something went wrong"));
        };
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @RequestBody @Valid Product product,
            Errors errors){

        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        String check=productService.update(id,product);

        return switch (check){
            case "false" -> ResponseEntity.status(400).body(new ApiResponse("id already use"));
            case "true" -> ResponseEntity.status(200).body(new ApiResponse("Update Successfully"));
            case "category id not found" -> ResponseEntity.status(400).body(new ApiResponse("Category ID not found"));
            case "id not found " -> ResponseEntity.status(400).body(new ApiResponse("id not found"));
            default -> ResponseEntity.status(400).body(new ApiResponse("somethings errors"));
        };
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable String id){
        if(productService.delete(id)){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully "));
        }
        return ResponseEntity.status(400).body(new ApiResponse("id not found "));
    }
}


