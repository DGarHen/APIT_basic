package com.app.controller;

import java.util.Map;

public class PokemonApi extends ApiBase{
    public void VerifyApiData(Map<String, String> datatable) {
        for (Map.Entry<String,String> entry : datatable.entrySet()){
            if(entry.getKey().equals("login.id")){
                api.single_entry_contains_string(entry);
            }else{
                api.single_entry_contains(entry);
            }
        }
    }
}
