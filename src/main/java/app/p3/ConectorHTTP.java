/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.p3;

import app.Main;
import com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author thepuar
 */
public class ConectorHTTP {
    String laurl = "http://mapas.valencia.es/lanzadera/puntoInteres%20/fallasvalencia?radio=4000&lang=es&lat=39465212&lon=-374521&filtros=1";
    String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
    String authorization = "Basic aW5mb3JtYXR1cHY6dHlBMGJxVWU=";
    HttpURLConnection con;
    
    
        private final static Logger logger = LoggerFactory.getLogger(ConectorHTTP.class);


    public ConectorHTTP(){
        try{
        URL url = new URL(laurl);
        con = (HttpURLConnection) url.openConnection();
        }catch(MalformedURLException mue){}
        catch(IOException ioe){}
    }
    
    public String connectar(){
        try{
        con.setRequestMethod("GET");
        }catch(ProtocolException pe){logger.error("Estableciendo el get");}
        con.setRequestProperty("authorization", this.authorization);
        con.setRequestProperty("user-agent", this.userAgent);
        
        //Procesando la respuesta
        
        String inputLine;
        StringBuffer response = new StringBuffer();
        
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while((inputLine = in.readLine())!=null)
                response.append(inputLine);
        in.close();
        }catch(IOException ioe){
            logger.error("Montando mensaje");
        }
        return response.toString();
    }
    
    
}
