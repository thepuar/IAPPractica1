/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.p3;

/**
 *
 * @author thepuar
 */
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {
	
	private String NOMBRE_EXCHANGE;
	private Channel channel;
	private Connection connection;
	
	public Publisher(String exchange) {
		NOMBRE_EXCHANGE=exchange;
	}

	public void connect() throws Exception{
		
		ConnectionFactory factory = new ConnectionFactory(); 
		factory.setHost("localhost"); 
		
		connection = factory.newConnection(); 
		
		channel = connection.createChannel();
		 
		channel.exchangeDeclare(NOMBRE_EXCHANGE, BuiltinExchangeType.TOPIC);
	
	}
	
	public void send(String tema, Object obj)throws Exception{
		
		channel.basicPublish(NOMBRE_EXCHANGE, tema.trim(), null, obj.toString().getBytes());
	
	}
	
	public void close() throws Exception{
		
		channel.close();
		connection.close();

	}

}

