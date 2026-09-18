package com.example.capstone1.Service;

import com.example.capstone1.Mudel.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class MerchantStockService {


    private final MerchantService merchantService;
    private final ProductService productService;

    ArrayList<MerchantStock> stocks = new ArrayList<>();


    public ArrayList<MerchantStock> get() {
        return stocks;
    }




    public String add(MerchantStock merchantStock){

        for(MerchantStock m:stocks){
            if(m.getId().equals(merchantStock.getId())){
                return "this ID already use";
            }
        }

        boolean productId=false;
        boolean merchantId=false;

        for(Product product:productService.get()){
            if (product.getId().equals(merchantStock.getProductid())) {
                productId = true;
                break;
            }
        }
        for(Merchant merchant:merchantService.get()){
            if(merchant.getId().equals(merchantStock.getMerchantId())){
                merchantId=true;
                break;
            }
        }
        if(!productId){
            return "Product ID Not found";
        }
        if (!merchantId)
            return "Merchant ID not found";

        for (MerchantStock m:stocks){
            if(m.getProductid().equals(merchantStock.getProductid())&&m.getMerchantId().equals(merchantStock.getMerchantId())){
                return "this Product already have relation with merchant";
            }
        }

        stocks.add(merchantStock);
        return "true";
    }



    public String update(String id ,MerchantStock merchantStock){

        boolean merchantStock1=false;

        for (MerchantStock m:stocks){
            if(m.getId().equals(id)){
                merchantStock1=true;
            }
        }

        if(merchantStock1) {

            for (MerchantStock m : stocks) {
                if (!m.getId().equals(id) && m.getId().equals(merchantStock.getId())) {
                    return "this ID already use";
                }
            }

            boolean productId = false;
            for (Product product : productService.get()) {
                if (product.getId().equals(merchantStock.getProductid())) {
                    productId = true;
                    break;
                }
            }

            boolean merchantId = false;
            for (Merchant merchant : merchantService.get()) {
                if (merchant.getId().equals(merchantStock.getMerchantId())) {
                    merchantId = true;
                    break;
                }
            }
            if (!productId) {
                return "Product ID Not found";
            }
            if (!merchantId) {
                return "Merchant ID not found";
            }

            for (MerchantStock m:stocks){
                if(!m.getId().equals(id)
                        &&(m.getProductid().equals(merchantStock.getProductid())
                        &&m.getMerchantId().equals(merchantStock.getMerchantId()))){
                    return "this Product already have relation with merchant";
                }
            }



            for (int i = 0; i < stocks.size(); i++) {
                if (stocks.get(i).getId().equals(id)) {
                    stocks.set(i, merchantStock);
                    return "true";
                }
            }
        }
    return "Merchant Stock ID not found for update";

    }



    public boolean delete(String id ){
        for(MerchantStock m:stocks){
            if(m.getId().equals(id)){
                stocks.remove(m);
                return true;
            }
        }
        return false;
    }





   public boolean  addStock(String merchantStockID, String productID, int amount) {

        for(MerchantStock m:stocks) {
            if (m.getId().equals(merchantStockID)) {

                if (m.getProductid().equals(productID)) {
                    m.setStock(m.getStock() + amount);
                    return true;
                }
            }
        }

       return false;
    }
}



