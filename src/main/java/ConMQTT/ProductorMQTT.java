/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConMQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author thepuar
 */
public class ProductorMQTT {

    MqttClient client;
    //Para iniciar el servicio
    // /usr/local/sbin/mosquitto || brew services start/stop mosquitto
    //Publicar mosquitto_pub -h 127.0.0.1 -t iot_data -m "Hello world"  --> -t es el topic
    //Consumir mosquitto_sub -h 127.0.0.1 -t iot_data

    private final static Logger logger = LoggerFactory.getLogger(ProductorMQTT.class);

    String topic = "";
    public ProductorMQTT(String ip, String puerto, String topic) {
        this.topic = topic;
        try {
            //Puerto por defecto 1883
            client = new MqttClient("tcp://" + ip + ":" + puerto, MqttClient.generateClientId());
            client.connect();
        } catch (MqttException mqe) {
            logger.error("Error en MQTT");
        }
        logger.info("Se ha creado un Productor MQTT con IP {}:{} ->Topic: {}",ip,puerto,topic);
    }

    public void sendMessage(String texto) {

        MqttMessage message = new MqttMessage();
        message.setPayload(texto.getBytes());
        try {
            client.publish(this.topic, message);
        } catch (MqttException mqe) {
            logger.error("Error enviando mensaje MQTT");
        }

    }

    public void close() {
        try {
            client.disconnect();
        } catch (MqttException mqe) {
            System.out.println("Error cerrando conexión MQTT.");
        }
    }
}
