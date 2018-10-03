/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConMQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author thepuar
 */
public class ConsumidorMQTT {
            private final static Logger logger = LoggerFactory.getLogger(ConsumidorMQTT.class);


    public ConsumidorMQTT(String uri, String topic) {
        try{
        MqttClient client = new MqttClient(uri, MqttClient.generateClientId());
        client.setCallback(new SimpleMQTTCallback());
        client.connect();
        client.subscribe(topic);
        }catch(MqttException mqe){
            logger.error("Error conectando el cliente de MQTT");
        }
        logger.info("ConsumidorMQTT creado con {} -> Topic: {}",uri,topic);
        
    }

}
