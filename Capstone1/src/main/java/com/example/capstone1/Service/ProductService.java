package com.example.capstone1.Service;

import com.example.capstone1.Mudel.Category;
import com.example.capstone1.Mudel.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ProductService {

    private final CategoryService categoryService;


    ArrayList<Product>products=new ArrayList<>();


    public ArrayList<Product>get(){
        return products;
    }

    public String add(Product product){
        boolean categoryFound = false;

        for(Category category : categoryService.getCategories()){
            if(category.getId().equals(product.getCategoryId())){
                categoryFound = true;
                break;
            }
        }

        if(!categoryFound){
            return "category id not found";
        }

        for(Product p : products){
            if(p.getId().equals(product.getId())){
                return "product id already use";
            }
        }

        products.add(product);
        return "true";
    }


    public String update(String id, Product product) {

        boolean categoryFound = false;

        for (Category category : categoryService.getCategories()) {
            if (category.getId().equals(product.getCategoryId())) {
                categoryFound = true;
                break;
            }
        }

        if (!categoryFound) {
            return "category id not found";
        }

        for (Product p : products) {
            if (!p.getId().equals(id)
                    && p.getId().equals(product.getId())) {
                return "false";
            }
        }

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return "true";
            }
        }

        return "id not found ";
    }

    public boolean delete(String id ){
        for(Product p:products){
            if(p.getId().equals(id)){
                products.remove(p);
                return true;
            }
        }
        return false;
    }
}

