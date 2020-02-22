/**
 * 
 */
package com.chao.spring_activeMQ;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jca.cci.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.sun.tools.internal.ws.processor.generator.JwsImplGenerator;

/**
 * Title: Spring_activeMQ.java
 * 
 * @author ChaoSir
 * @date 2019年10月9日
 * @version 1.0
 */
public class Spring_activeMQ_send {

	@Test
	public void send() throws Exception {

		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(	"classpath:spring/applicationContext-activemq.xml");
		JmsTemplate jmsTemplate = classPathXmlApplicationContext.getBean(JmsTemplate.class);
		Queue queue = (Queue) classPathXmlApplicationContext.getBean("activeMQQueue");
		jmsTemplate.send(queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage();
				message.setText("由spring管理的activeMQ");
				return message;
			}
		});

	}

}
