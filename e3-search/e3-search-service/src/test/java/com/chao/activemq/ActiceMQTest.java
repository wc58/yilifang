/**
 * 
 */
package com.chao.activemq;

import static org.junit.Assert.*;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**  
* Title: ActiceMQTest.java  
* @author ChaoSir 
* @date 2019年10月7日  
* @version 1.0  
*/
public class ActiceMQTest {

	@Test
	public void producer() throws Exception {
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.134:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue createQueue = session.createQueue("activce-test");
		MessageProducer producer = session.createProducer(createQueue);
		TextMessage createTextMessage = session.createTextMessage("this is one acticeMQ");
		producer.send(createTextMessage);
		
		producer.close();
		session.close();
		connection.close();
		
	}
	
	@Test
	public void customer() throws Exception {
		
		ConnectionFactory connectionFactory  = new ActiveMQConnectionFactory("tcp://192.168.25.134:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue("activce-test");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage  = (TextMessage) message;
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		System.in.read();
		consumer.close();
		session.close();
		connection.close();
		
	}
	
	@Test
	public void producer1() throws Exception {
		
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.134:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("activce-topic");
		MessageProducer producer = session.createProducer(topic);
		TextMessage message = session.createTextMessage("dsadffdas");
		producer.send(message);
		
		producer.close();
		session.close();
		connection.close();
		
	}
	
	@Test
	public void customer2() throws Exception {
		
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.134:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("activce-topic");
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		System.in.read();
		consumer.close();
		session.close();
		connection.close();
		
	}
	
}
