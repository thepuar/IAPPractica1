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
import java.util.Calendar;
import java.util.concurrent.TimeoutException;
import org.json.JSONObject;

/**
 *
 * @author thepuar
 */
public class Productor2JX {

    ConnectionFactory factory;
    Connection connection;
    Channel channel;
    private static String NOMBRE_EXCHANGE = "";

    public Productor2JX(String nomExchange) {
        this.NOMBRE_EXCHANGE = nomExchange;
        this.factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            System.out.println("Error al conectar --> " + e.getLocalizedMessage());
        } catch (TimeoutException toe) {
            System.out.println("Error de timeout --> " + toe.getLocalizedMessage());
        }
        System.out.println("Se ha creado un Productor con un Exchange " + NOMBRE_EXCHANGE);
    }

    public void connectar() {
        try {
            channel = connection.createChannel();
            channel.exchangeDeclare(NOMBRE_EXCHANGE, BuiltinExchangeType.TOPIC);
        } catch (IOException ioe) {
            System.out.println("Error al conectar");
        }
    }

    public void sendMessage(String texto, String topic) {
        try {
            JSONObject json = jsonMaker(texto);
            channel.basicPublish(NOMBRE_EXCHANGE, topic, null, json.toString().getBytes());
        } catch (IOException ioe) {
            System.out.println("Error enviando el mensaje");
        }
        System.out.println("Enviando mensaje con Topic: " + topic + " --->");
    }

    public JSONObject jsonMaker(String texto) {
        JSONObject json = new JSONObject();
        json.put("Fecha", Calendar.getInstance().getTime());
        json.put("Texto", texto);
        return json;
    }

    public void close() {
        try {
            channel.close();
            connection.close();
        } catch (IOException ioe) {
            System.out.println("Error cerrando ");
        } catch (TimeoutException toe) {
            System.out.println("Error cerrando timeout");
        }
    }

}
