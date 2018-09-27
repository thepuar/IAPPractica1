/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.p1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author thepuar
 */
public class Productor {

    ConnectionFactory factory;
    Connection connection;
    Channel channel;
    private final static String NOMBRE_COLA = "saludo";

    public Productor() {
        this.factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            System.out.println("Error al conectar --> "+e.getLocalizedMessage());
        } catch (TimeoutException toe) {
            System.out.println("Error de timeout --> "+ toe.getLocalizedMessage());
        }
    }

    public void connectar() {
        try {
            channel = connection.createChannel();
            channel.queueDeclare(NOMBRE_COLA, false, false,false,null);
        } catch (IOException ioe) {
            System.out.println("Error al conectar");
        }
    }
    
    public void sendMessage(String texto){
        try{
        channel.basicPublish("", NOMBRE_COLA, null, texto.getBytes());
        }catch(IOException ioe){System.out.println("Error enviando el mensaje");}
        System.out.println("Enviando mensaje --->");
    }
    
    public void close(){
        try{
        channel.close();
        connection.close();
        }catch(IOException ioe){System.out.println("Error cerrando ");}
        catch(TimeoutException toe){System.out.println("Error cerrando timeout");}
    }

}
