package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Mudel.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@AllArgsConstructor
public class MerchantController
{
    private final MerchantService merchantService;




    @GetMapping("/get")
    public ResponseEntity<?>get(){
        ArrayList<Merchant>merchants=merchantService.get();
        return ResponseEntity.status(200).body(merchants);
    }


    @PostMapping("/add")
    public ResponseEntity<?>add(@RequestBody @Valid Merchant merchant , Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        if (merchantService.add(merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("Add successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("This id already use "));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?>update(@PathVariable String id , @RequestBody @Valid Merchant merchant,Errors errors){

        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        String check=merchantService.update(id,merchant);
        return switch (check){
            case "false"->ResponseEntity.status(200).body(new ApiResponse("id already use"));
            case "id not found"->ResponseEntity.status(400).body(new ApiResponse("id not found"));
            case "true"->ResponseEntity.status(200).body(new ApiResponse("Update Successfully"));
            default -> ResponseEntity.status(400).body(new ApiResponse("somethings errors"));
        };
    }



@DeleteMapping("/Delete/{id}")
    public ResponseEntity<?>delete(@PathVariable String id){
        if(merchantService.delete(id)){
            return ResponseEntity.status(200).body(new ApiResponse("deleted successfully "));
        }
        return ResponseEntity.status(200).body(new ApiResponse("id not found "));
    }
}
