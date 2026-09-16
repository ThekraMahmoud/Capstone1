package com.example.capstone1.Controller;


import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Mudel.MerchantStock;
import com.example.capstone1.Mudel.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity<?> get() {
        ArrayList<User> users = userService.get();
        return ResponseEntity.status(200).body(users);
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        if (userService.add(user)) {
            return ResponseEntity.status(200).body(new ApiResponse("Add successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("id already use"));

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        String check = userService.update(id, user);
        return switch (check) {
            case "false" -> ResponseEntity.status(400).body(new ApiResponse("id already use"));
            case "id not found" -> ResponseEntity.status(400).body(new ApiResponse("id not found "));
            case "true" -> ResponseEntity.status(200).body(new ApiResponse("update successfully"));
            default -> ResponseEntity.status(400).body(new ApiResponse("somethings errors"));
        };
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (userService.delete(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Remove " + id + " successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("id not found "));
    }


    @PostMapping("/update/puy-prodect/{userID}/{productID}/{merchantId}/{puyStock}")
    public ResponseEntity<?> buyProductBayUser(@PathVariable String userID, @PathVariable String productID, @PathVariable String merchantId, @PathVariable int puyStock) {

        String check = userService.bayProduct(userID, productID, merchantId, puyStock);

        if (check.startsWith("true")) {
            return ResponseEntity.status(200).body(new ApiResponse("Product purchased successfully."));
        }
        return switch (check) {
            case "user id not found " -> ResponseEntity.status(400).body(new ApiResponse("User ID not found"));
            case "product id not found " -> ResponseEntity.status(400).body(new ApiResponse("Product ID not found"));
            case "merchant id not found" -> ResponseEntity.status(400).body(new ApiResponse("Merchant ID not found"));
            case "Merchant does not have this product" -> ResponseEntity.status(400).body(new ApiResponse("Merchant does not have this product"));
            case "not have enough stock" -> ResponseEntity.status(400).body(new ApiResponse("Not enough stock"));
            case "not enough money" -> ResponseEntity.status(400).body(new ApiResponse("Not have enough money"));
            case "Admin cannot purchase products" -> ResponseEntity.status(400).body(new ApiResponse("Admin cannot purchase products"));
            case "purchase amount must be greater than zero" -> ResponseEntity.status(400).body(new ApiResponse("Purchase amount must be greater than zero"));

            default -> ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        };
    }



    @PostMapping("/prime/{userId}")
    public ResponseEntity<?> prime(@PathVariable String userId){

        String check = userService.userPrime(userId);

        return switch (check){

            case "true" -> ResponseEntity.status(200).body(new ApiResponse("Prime subscription successfully"));

            case "id not found" -> ResponseEntity.status(400).body(new ApiResponse("User ID not found"));

            case "not have money" -> ResponseEntity.status(400).body(new ApiResponse("Not enough money"));

            default -> ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        };
    }


    @GetMapping("/get/user/prodect/{id}")
    public ResponseEntity<?> getProductPuy(@PathVariable String id) {
        ArrayList<MerchantStock> getPuy = userService.getProductPuy(id);
        if (getPuy.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("You haven't purchased any products yet. If you'd like to start shopping, feel free to visit our store  " + "Visit our store: http://localhost:8080/api/v1/stock/get"));
        }
        return ResponseEntity.status(200).body(getPuy);
    }

    @PostMapping("/family/{sender}/{receiver}")
    public ResponseEntity<?> sendFamilyRequest(@PathVariable String sender, @PathVariable String receiver) {
        String check = userService.sendFamilySystemRequest(sender, receiver);
        return switch (check) {
            case "user one not found" -> ResponseEntity.status(400).body(new ApiResponse("Sender ID not found"));
            case "user Tow not found" -> ResponseEntity.status(400).body(new ApiResponse("Receiver ID not found"));
            case "You cannot send a family request to an Admin" -> ResponseEntity.status(400).body(new ApiResponse("You cannot send a family request to an Admin"));
            case "Admin cannot use Family System" -> ResponseEntity.status(400).body(new ApiResponse("Admin cannot use Family System"));
            case " cannot send request to yourself" -> ResponseEntity.status(400).body(new ApiResponse(" cannot send request to yourself @_@"));
            case "PENDING" -> ResponseEntity.status(200).body(new ApiResponse("Family request sent successfully"));
            case "You are already family"->ResponseEntity.status(400).body(new ApiResponse("You are already family"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        };
    }

    @PutMapping("/family/response/{receiver}/{sender}/{accept}")
    public ResponseEntity<?> responseFamily(@PathVariable String receiver, @PathVariable String sender, @PathVariable Boolean accept) {
        String check = userService.responseFamily(receiver, sender, accept);
        return switch (check) {
            case "ACCEPTED" -> ResponseEntity.status(200).body(new ApiResponse("Family request accepted successfully"));
            case "REJECTED" -> ResponseEntity.status(200).body(new ApiResponse("Family request rejected"));
            case "request not found" -> ResponseEntity.status(400).body(new ApiResponse("Family request not found"));
            case "Request already sent"->ResponseEntity.status(200).body(new ApiResponse("Request already sent"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Something went wrong"));
        };
    }

    @GetMapping("get/MyFamily/{id}")
    public ResponseEntity<?> getMyFamily(@PathVariable String id) {
        ArrayList<String> getFamily = userService.showFamily(id);        if (getFamily.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("You don't have a family yet. If you want to add a family member, send a request. To respond to a family request, visit: /api/v1/user/family/response/{receiver}/{sender}/{accept"));
        }
        return ResponseEntity.status(200).body(getFamily);
    }



    @GetMapping("/family/check-purchase/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> checkFamilyPurchase(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {

        ArrayList<String> check =
                userService.checkFamilyPurchase(userId, productId, merchantId);

        if (check.isEmpty()) {
            return ResponseEntity.status(200)
                    .body(new ApiResponse("No family member has purchased this product from this merchant"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Your family members with IDs " + check + " have already purchased this product from this merchant"));
    }

}

