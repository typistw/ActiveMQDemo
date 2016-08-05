package com.active.mq.demo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author weijinsheng
 * @data 2016年7月28日 上午9:52:37
 */
public class JMSConsumer {
	
	public static void main(String[] args) {
		Connection connection = null;
		Session session = null;//会话，接受或者发送消息线程
		Destination destination;//消息目的地
		MessageConsumer messageConsumer;//消息的消费者
		
		try {
			connection = MQConnectionFactory.getConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//创建一个连接HelloMQ的消息队列
			destination = session.createQueue("HelloMQ");
			//创建消息消费者
			messageConsumer = session.createConsumer(destination);
			
			while(true){
				TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
				if(textMessage != null){
					System.out.println("收到的消息：" + textMessage.getText());
				}else{
					break;
				}
			}
			//提交会话
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
			if(session != null){
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
