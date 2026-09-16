package com.example.capstone1.Service;

import com.example.capstone1.Mudel.Merchant;
import com.example.capstone1.Mudel.MerchantStock;
import com.example.capstone1.Mudel.Product;
import com.example.capstone1.Mudel.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {


    //private final MerchantStockService merchantStockService;
    private final MerchantService merchant;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    //

    ArrayList<User> users = new ArrayList<>();
    ArrayList<User>prime=new ArrayList<>();


    public ArrayList<User> get() {
        return users;
    }

    public boolean add(User user) {
        for (User p : users) {
            if (p.getId().equals(user.getId())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }


    public String update(String id, User user) {
        boolean found = false;

        for (User user2 : users) {
            if (user2.getId().equals(id)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return "id not found";
        }


        for (User user1 : users) {
            if (!user1.getId().equals(id) && user1.getId().equals(user.getId())) {
                return "false";
            }
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return "true";
            }
        }

        return "errors";
    }


    public boolean delete(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }


    ArrayList<MerchantStock> getAllOrders = new ArrayList<>();
    ArrayList<String> userIdForPuy = new ArrayList<>();

    public String bayProduct(String userID, String productID, String merchantId, int puyStock) {
        ArrayList<Merchant> merchants = merchant.get();
        ArrayList<Product> products = productService.get();
        ArrayList<MerchantStock> merchantStocks = merchantStockService.get();

        boolean userID1 = false;
        boolean productID1 = false;
        boolean merchantID1 = false;
        boolean stockFound = false;
        double balance = 0;
        double price = 0;
        int oldStock = 0;
        User currentUser = null;






        for (User user : users) {
            if (user.getId().equals(userID)) {
                currentUser = user;
                balance = currentUser.getBalance();
                userID1 = true;
                if (currentUser.getRole().equalsIgnoreCase("Admin")) {
                    return "Admin cannot purchase products";
                }
                break;
            }
        }

        boolean prime1=false;
        for(User p:prime){
            if(p.getId().equals(userID)){
                prime1=true;
            }
        }
        for (Product product : products) {
            if (product.getId().equals(productID)) {
                price = product.getPrice();
                productID1 = true;

                if (prime1) {
                    price = price - 4.99;
                }
                break;
            }
        }
        for (Merchant m : merchants) {
            if (m.getId().equals(merchantId)) {
                merchantID1 = true;
                break;
            }

        }

        if (!userID1) {
            return "user id not found ";
        }

        if (!productID1) {
            return "product id not found ";
        }

        if (!merchantID1) {
            return "merchant id not found";
        }


        if (puyStock <= 0) {
            return "purchase amount must be greater than zero";
        }

        ArrayList<String> familyMembers =
                checkFamilyPurchase(userID, productID, merchantId);

        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductid().equals(productID) && stock.getMerchantId().equals(merchantId)) {
                stockFound = true;
                oldStock = stock.getStock();

                double totalPrice = puyStock * price;

                if (puyStock > oldStock) {
                    return "not have enough stock";
                }

                if (balance < totalPrice) {
                    return "not enough money";
                }
//سويت الاري عشان ابغا فقط القيمه الي طلبها المستخدم
                MerchantStock order = new MerchantStock(
                        stock.getId(),
                        stock.getProductid(),
                        stock.getMerchantId(),
                        puyStock
                );



                oldStock = oldStock - puyStock;
                stock.setStock(oldStock);

                double newBalance = balance - totalPrice;
                currentUser.setBalance(newBalance);

                getAllOrders.add(order);
                userIdForPuy.add(userID);
            }
        }

        if (!stockFound) {
            return "Merchant does not have this product";
        }

        if (!familyMembers.isEmpty()) {
            return "true - Family members: " + familyMembers;
        }

        return "true";
    }


    public ArrayList<MerchantStock> getProductPuy(String userID) {
        ArrayList<MerchantStock> orders = new ArrayList<>();
        //userIdForPuy --> بس فيه الادي حق المستخدم
        for (int i = 0; i < userIdForPuy.size(); i++) {
            if (userIdForPuy.get(i).equals(userID)) {
                //اخذ قيمة i فقط
                orders.add(getAllOrders.get(i));
            }
        }
        return orders;
    }


    public String userPrime(String userId) {
        String userID = null;
        for (User user : users) {
            if (user.getId().equals(userId)) {
                if (user.getRole().equalsIgnoreCase("Admin")) {
                    return "Admin cannot purchase Prime";
                }
                userID = user.getId();
                if (user.getBalance() >= 100) {
                    user.setBalance(user.getBalance() - 100);
                    prime.add(user);
                    return "true";
                }
            }
        }
        if(userID==null){
            return "id not found";
        }
        return "not have money";
        }





///family System


ArrayList<ArrayList<String>> familyRequests = new ArrayList<>();
        public String sendFamilySystemRequest(String sender ,String receiver){
        boolean userOneFound=false;
        boolean userTwoFound =false;
            String status ;
            String senderId =null;
            String requestId =null;

            for(User userID:users){
                if(userID.getId().equals(sender)){
                    if(userID.getRole().equalsIgnoreCase("Admin")){
                        return "Admin cannot use Family System";
                    }

                    senderId =userID.getId();
                    userOneFound=true;
                }
                if(userID.getId().equals(receiver)){

                    if(userID.getRole().equalsIgnoreCase("Admin")){
                        return "You cannot send a family request to an Admin";
                    }

                    requestId=userID.getId();
                    userTwoFound=true;
                }
            }


        if(!userOneFound){
         return "user one not found";
        }
        if(!userTwoFound){
            return "user Tow not found";
        }

        if(sender.equals(receiver)){
            return " cannot send request to yourself";
        }
        status = "PENDING";

            ArrayList<String> request = new ArrayList<>();
            request.add(senderId);
            request.add(requestId);
            request.add(status);

            for (ArrayList<String> checkRequest : familyRequests) {

                if ((checkRequest.get(0).equals(senderId) && checkRequest.get(1).equals(requestId))
                        || (checkRequest.get(0).equals(requestId) && checkRequest.get(1).equals(senderId))) {

                    if (checkRequest.get(2).equals("ACCEPTED")) {
                        return "You are already family";
                    }

                    if (checkRequest.get(2).equals("PENDING")) {
                        return "Request already sent";
                    }
                }
            }




            familyRequests.add(request);
          return "PENDING";
        }



     public String responseFamily(String receiver,String sender,Boolean accept){
         String status ;

         for (ArrayList<String> request : familyRequests) {
             if (request.get(0).equals(sender)&&request.get(1).equals(receiver)) {
                 status = request.get(2);

                 if (status.equals("PENDING")) {
                     if(accept) {
                         request.set(2, "ACCEPTED");
                         return "ACCEPTED";
                     }
                     request.set(2, "REJECTED");
                     return "REJECTED";
                 }
                 return status;
             }
         }
         return "request not found";
     }



    public ArrayList<String> showFamily(String id) {

        ArrayList<String> showFamily = new ArrayList<>();

        for (ArrayList<String> search : familyRequests) {

            if (search.get(2).equals("ACCEPTED")) {

                String familyMemberId = null;

                if (search.get(0).equals(id)) {
                    familyMemberId = search.get(1);
                }
                else if (search.get(1).equals(id)) {
                    familyMemberId = search.get(0);
                }

                if (familyMemberId != null) {

                    for (User user : users) {
                        if (user.getId().equals(familyMemberId)) {
                            showFamily.add(user.getUserName());
                            break;
                        }
                    }
                }
            }
        }

        return showFamily;
    }


//
    public ArrayList<ArrayList<String>> familyOrder(String id){
        ArrayList<ArrayList<String>>showFamilyOrder=new ArrayList<>();

        for(ArrayList<String> search :familyRequests){
            if(search.get(0).equals(id)|| search.get(1).equals(id)){
                showFamilyOrder.add(search);

            }
        }
        return showFamilyOrder;
    }







    public ArrayList<String> checkFamilyPurchase(String userId, String productId, String merchantId) {

        ArrayList<String> familyMembers = new ArrayList<>();

        for (ArrayList<String> foundUser : familyRequests) {
            if (foundUser.get(2).equals("ACCEPTED")) {
                String familyMember = null;

                if (foundUser.get(0).equals(userId)) {
                    familyMember = foundUser.get(1);
                }

                else if (foundUser.get(1).equals(userId)) {
                    familyMember = foundUser.get(0);
                }

                if (familyMember == null) {
                    continue;
                }

                for (int i = 0; i < userIdForPuy.size(); i++) {

                    if (userIdForPuy.get(i).equals(familyMember)
                            && getAllOrders.get(i).getProductid().equals(productId)
                            && getAllOrders.get(i).getMerchantId().equals(merchantId)) {


                        for (User user : users) {
                            if (user.getId().equals(familyMember)) {
                                familyMembers.add(user.getUserName());
                                break;
                            }
                        }
                    }
                }
            }
        }

        return familyMembers;
    }


// to use in trending
    public ArrayList<MerchantStock> getAllOrders() {
        return getAllOrders;
    }



    }





