/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.p1;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.impl.AMQBasicProperties;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author thepuar
 */
public class Consumidor {

    private static final String NOMBRE_COLA = "saludo";
    ConnectionFactory factory;
    Connection connection;
    Channel channel;

    public Consumidor() {
        System.out.println("Iniciando consumidor.");
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(NOMBRE_COLA, false, false, false, null);
            channel.basicConsume(NOMBRE_COLA, true,consumer);
        } catch (IOException ioe) {
            System.out.println("Error al crear el consumidor --> "+ioe.getLocalizedMessage());
        } catch (TimeoutException toe) {
            System.out.println("Error al crear el consumidor --> "+toe.getLocalizedMessage());
        }

    }

    Consumer consumer = new DefaultConsumer(channel) {

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String mensaje = new String(body,"UTF-8");
            if(mensaje.equals("cerrar")&&connection.isOpen())
                    connection.close();
           
            
            System.out.println("Recibido mensaje '"+mensaje+"'");
            try{
            Thread.sleep(2000);
            }catch(InterruptedException ie){System.out.println("Error con Sleep");}
            
            
            
        }
    };
    
    
}
