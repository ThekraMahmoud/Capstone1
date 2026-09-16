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





   public String  addStock(String merchantStockID, String productID, int amount) {

       String merchantID1=null;
       String productID1=null;

        for(MerchantStock m:stocks) {
            if (m.getId().equals(merchantStockID)) {

                if (m.getMerchantId().equals(merchantStockID)) {
                    merchantID1 = m.getMerchantId();
                }
                if (!m.getProductid().equals(productID)) {
                    productID1 = m.getProductid();

                }
            }

            if (productID1 == null) {
                return "productID errors";
            }
            if (merchantID1 == null) {
                return "merchantID errors";
            }


            for (MerchantStock m1 : stocks) {
                if (m1.getProductid().equals(productID) && m1.getMerchantId().equals(merchantStockID)) {
                    m1.setStock(m1.getStock() + amount);
                    return "true";
                }
            }
        }
       return "Merchant Stock ID not found. Please check the ID and try again.";
    }
}



