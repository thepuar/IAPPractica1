/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.p2;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author thepuar
 */
public class Productor2 {

    ConnectionFactory factory;
    Connection connection;
    Channel channel;
    private static String NOMBRE_EXCHANGE = "";

    public Productor2(String nomExchange) {
        this.NOMBRE_EXCHANGE = nomExchange;
        this.factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            System.out.println("Error al conectar --> "+e.getLocalizedMessage());
        } catch (TimeoutException toe) {
            System.out.println("Error de timeout --> "+ toe.getLocalizedMessage());
        }
        System.out.println("Se ha creado un Productor con un Exchange "+NOMBRE_EXCHANGE);
    }

    public void connectar() {
        try {
            channel = connection.createChannel();
            channel.exchangeDeclare(NOMBRE_EXCHANGE, BuiltinExchangeType.TOPIC);
        } catch (IOException ioe) {
            System.out.println("Error al conectar");
        }
    }
    
    public void sendMessage(String texto, String topic){
        try{
        channel.basicPublish(NOMBRE_EXCHANGE, topic,null, texto.getBytes());
        }catch(IOException ioe){System.out.println("Error enviando el mensaje");}
        System.out.println("Enviando mensaje con Topic: "+topic+ " --->");
    }
    
    public void close(){
        try{
        channel.close();
        connection.close();
        }catch(IOException ioe){System.out.println("Error cerrando ");}
        catch(TimeoutException toe){System.out.println("Error cerrando timeout");}
    }

}

