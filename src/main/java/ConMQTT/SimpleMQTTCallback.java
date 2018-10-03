/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConMQTT;

/**
 *
 * @author thepuar
 */

import app.Main;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleMQTTCallback implements MqttCallback{
    
        private final static Logger logger = LoggerFactory.getLogger(SimpleMQTTCallback.class);


    @Override
    public void connectionLost(Throwable thrwbl) {
        logger.error("Error conectando");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        System.out.println("Mensaje recibido: "+new String(mm.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        
    }
    
}
