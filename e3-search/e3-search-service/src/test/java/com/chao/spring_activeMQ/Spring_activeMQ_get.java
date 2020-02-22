/**
 * 
 */
package com.chao.spring_activeMQ;

import static org.junit.Assert.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**  
* Title: Spring_activeMQ_get.java  
* @author ChaoSir 
* @date 2019年10月9日  
* @version 1.0  
*/
public class Spring_activeMQ_get implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println(textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testName() throws Exception {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		System.in.read();
	}

}
