package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Mudel.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;





    @GetMapping("/get")
    public ResponseEntity<?>get(){
        ArrayList<Category>categories=categoryService.getCategories();
        return ResponseEntity.status(200).body(categories);
    }



    @PostMapping("/add")
    public ResponseEntity<?>add(@RequestBody@Valid Category category, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        if(categoryService.add(category)){
            return ResponseEntity.status(200).body(new ApiResponse("Add successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("id already use"));

    }



    @PutMapping("/update/{id}")
    public ResponseEntity<?>update(@PathVariable String id ,@RequestBody @Valid Category category ,Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        String check=categoryService.update(id,category);
        return switch (check){
            case "false"->ResponseEntity.status(400).body(new ApiResponse("id already use"));
            case "id not found"->ResponseEntity.status(400).body(new ApiResponse("id not found "));
            case "true"->ResponseEntity.status(200).body(new ApiResponse("update successfully"));
            default -> ResponseEntity.status(400).body(new ApiResponse("somethings errors"));
        };
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable String id){
        if(categoryService.delete(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Remove "+id+" successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("id not found "));
    }


    @GetMapping("/trending")
    public ResponseEntity<?> trending(){
        ArrayList<Category> trending = categoryService.trending();
        return ResponseEntity.status(200).body(trending);
    }
}
