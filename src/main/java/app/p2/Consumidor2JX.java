/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.p2;

import app.Main;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

/**
 *
 * @author thepuar
 */
public class Consumidor2JX {
    
    private static  String NOMBRE_EXCHANGE = "Deportes";
    String COLA_CONSUMER;
    ConnectionFactory factory;
    Connection connection;
    Channel channel;

     public Consumidor2JX(String nomExchange, String topic) {
        System.out.println("Iniciando consumidor.");
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            NOMBRE_EXCHANGE = nomExchange;
            connection = factory.newConnection();
            channel = connection.createChannel();
            
            //DIFERENTE A LA PRACTICA ANTERIOR
            channel.exchangeDeclare(NOMBRE_EXCHANGE, BuiltinExchangeType.TOPIC);
            COLA_CONSUMER = channel.queueDeclare().getQueue();
            channel.queueBind(COLA_CONSUMER, NOMBRE_EXCHANGE, topic);
            channel.basicConsume(COLA_CONSUMER,true, consumer);
            //
        } catch (IOException ioe) {
            System.out.println("Error al crear el consumidor --> "+ioe.getLocalizedMessage());
        } catch (TimeoutException toe) {
            System.out.println("Error al crear el consumidor --> "+toe.getLocalizedMessage());
        }
         System.out.println("Se ha creado un consumidor a un Exchange "+NOMBRE_EXCHANGE+" y un Topic "+topic);

    }
     
      Consumer consumer = new DefaultConsumer(channel) {

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String mensaje = new String(body,"UTF-8");
            JSONObject json = new JSONObject(mensaje);
            
            if(mensaje.equals("cerrar")&&connection.isOpen())
                    connection.close();
           
            System.out.println("Recibido mensaje en JSON\n"+json.toString()+"'");
            System.out.println("Convirtiendo a XML");
            Document xml = jsonToXmlMaker(json);
            System.out.println("Mostrando XML");
            System.out.println(xml.asXML());
            try{
            Thread.sleep(2000);
            }catch(InterruptedException ie){System.out.println("Error con Sleep");}
            
        }
        
        public Document jsonToXmlMaker(JSONObject json){
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("Mensajes");
            Element mensaje = root.addElement("Mensaje");
            mensaje.addElement("Fecha").addText(json.get("Fecha").toString());
            mensaje.addElement("Texto").addText(json.get("Texto").toString());
            return document;
        }
    };
      
    
}
