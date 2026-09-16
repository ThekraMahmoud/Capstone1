package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Mudel.Merchant;
import com.example.capstone1.Mudel.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api/v1/stock")
@AllArgsConstructor
public class MerchantStockController {


    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        ArrayList<MerchantStock> merchantStocks=merchantStockService.get();
        return ResponseEntity.status(200).body(merchantStocks);
    }


    @PostMapping("/add")
    public ResponseEntity<?>add(@RequestBody @Valid MerchantStock merchantStock , Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }



        String check=merchantStockService.add(merchantStock);
        return switch (check){
            case "this ID already use"->ResponseEntity.status(400).body(new ApiResponse("this ID already use"));
            case "Product ID Not found"->ResponseEntity.status(400).body(new ApiResponse("Product ID Not found"));
            case "Merchant ID not found"->ResponseEntity.status(400).body(new ApiResponse("Merchant ID not found"));
            case "this Product already have relation with merchant"->ResponseEntity.status(400).body(new ApiResponse("this Product already have relation with merchant"));
            case "true"->ResponseEntity.status(200).body(new ApiResponse("add successfully"));
            default ->ResponseEntity.status(400).body(new ApiResponse("somethings errors"));
        };
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?>update(@PathVariable String id , @RequestBody @Valid MerchantStock merchantStock, Errors errors){

        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        String check=merchantStockService.update(id,merchantStock);
        return switch (check){
            case "Merchant Stock ID not found for update"->ResponseEntity.status(400).body(new ApiResponse("Merchant Stock ID not found for update"));
            case "this ID already use"->ResponseEntity.status(200).body(new ApiResponse("this ID already use"));
            case "Product ID Not found"->ResponseEntity.status(400).body(new ApiResponse("Product ID Not found"));
            case "Merchant ID not found"->ResponseEntity.status(400).body(new ApiResponse("Merchant ID not found"));
            case "this Product already have relation with merchant"->ResponseEntity.status(400).body(new ApiResponse("this Product already have relation with merchant"));
            case "true"->ResponseEntity.status(200).body(new ApiResponse("Update Successfully"));

            default -> ResponseEntity.status(400).body(new ApiResponse("somethings errors"));
        };
    }



    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<?>delete(@PathVariable String id){
        if(merchantStockService.delete(id)){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully "));
        }
        return ResponseEntity.status(200).body(new ApiResponse("id not found "));
    }

    @PostMapping("/addStock/{merchantID}/{productID}/{stock}")
    public ResponseEntity<?>addStock(@PathVariable String merchantID,@PathVariable String productID,@PathVariable int stock){
     String check=merchantStockService.addStock(merchantID,productID,stock);
        return switch (check){
          case "productID errors"->ResponseEntity.status(400).body((new ApiResponse("productID Not found")));
          case "merchantID errors"->ResponseEntity.status(400).body((new ApiResponse("merchantID Not found ")));
          case "true"->ResponseEntity.status(200).body("Add Successfully");
          case "Merchant Stock ID not found. Please check the ID and try again."->ResponseEntity.status(400).body(new ApiResponse("Merchant Stock ID not found. Please check the ID and try again."));

          default ->ResponseEntity.status(400).body(new ApiResponse("somethings errors"));
      };
    }


}
