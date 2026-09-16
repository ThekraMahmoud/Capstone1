package com.example.capstone1.Service;

import com.example.capstone1.Mudel.Category;
import com.example.capstone1.Mudel.MerchantStock;
import com.example.capstone1.Mudel.Product;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    private final UserService userService;
    private final ProductService productService;

    ArrayList<Category> categories = new ArrayList<>();

    public CategoryService(@Lazy UserService userService,
                           @Lazy ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }



    public ArrayList<Category> getCategories(){
        return categories;
    }


    public boolean add(Category category){
        for(Category c:categories){
            if(c.getId().equals(category.getId())){
                return false;
            }
        }
        categories.add(category);
        return true ;
    }



    public String update(String id,Category category){
        for (Category value : categories) {
            if (!value.getId().equals(id) && value.getId().equals(category.getId())) {
                return "false";
            }
        }
        for(int i=0;i<categories.size();i++){
            if(categories.get(i).getId().equals(id)){
                categories.set(i,category);
                return "true";
            }
        }
        return "id not found";
    }






    public boolean delete(String id){
        for(Category c:categories){
            if(c.getId().equals(id)){
                categories.remove(c);
                return true;
            }
        }
        return false;
    }





    public ArrayList<Category> trending(){
        ArrayList<Category> trending1 = new ArrayList<>();

        ArrayList<MerchantStock> orders = userService.getAllOrders();

        for (Category category : categories){
            int count = 0;

            for (MerchantStock order : orders){
                for (Product product : productService.get()){
                    if (order.getProductid().equals(product.getId())
                            && product.getCategoryId().equals(category.getId())) {
                        count++;
                        break;
                    }
                }
            }

            if (count > 4) {
                trending1.add(category);
            }
        }

        return trending1;
    }
}
