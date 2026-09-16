package com.example.capstone1.Service;


import com.example.capstone1.Mudel.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    ArrayList<Merchant>merchants=new ArrayList<>();


    public ArrayList <Merchant> get(){
        return merchants;
    }



    public boolean add(Merchant merchant){
        for(Merchant m:merchants) {
            if (m.getId().equals(merchant.getId())){
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }





    public String update(String id ,Merchant merchant){
        for(Merchant m:merchants){
            if(!m.getId().equals(id)&&m.getId().equals(merchant.getId())){
                return "false";
            }
        }

        for(int i =0;i<merchants.size();i++){
            if(merchants.get(i).getId().equals(id)){
                merchants.set(i,merchant);
                return "true";
            }
        }
        return "id not found";
    }


    public boolean delete(String id ){
        for(Merchant m:merchants){
            if(m.getId().equals(id)){
                merchants.remove(m);
                return true;
            }
        }
        return false;
    }
}
