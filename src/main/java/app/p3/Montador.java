/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.p3;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author thepuar
 */
public class Montador {
    
    String sJson;
    JSONArray ja;
    Publisher pub;
    
    public Montador(String cadena){
        this.sJson = cadena;
        ja = new JSONArray(sJson);
    }
    public void connect(){
        pub = new Publisher("fallas");
        try{
        pub.connect();
        }catch(Exception e){}
    }
    
    public void publish()throws Exception{
        int distancia;
        for(int i = 0; i<ja.length();i++){
            
            JSONObject obj = ja.getJSONObject(i);
            System.out.println("La falla "+obj.getString("titulo")+ " esta a "+obj.getInt("distancia")+" Metros");
            
            distancia = obj.getInt("distancia");
            if(distancia < 1000)
                pub.send("cerca", obj);
            else
                pub.send("lejos", obj);
            
            
        }
    }
    
}
