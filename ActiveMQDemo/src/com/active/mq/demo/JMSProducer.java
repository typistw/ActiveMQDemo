package com.active.mq.demo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author weijinsheng
 * @data 2016年7月28日 上午10:06:40
 */
public class JMSProducer {
	
	private static final int SENNUM = 10;//发送消息的数量
	
	public static void main(String[] args) {
		Connection connection = null;
		Session session = null;
		Destination destination;
		//消息生产者
		MessageProducer messageProducer;
		
		try {
			connection = MQConnectionFactory.getConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//创建一个名称为 HelloMQ 的消息队列
			destination = session.createQueue("HelloMQ");
			messageProducer = session.createProducer(destination);
			sendMessage(session, messageProducer);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session != null){
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
		for(int i=0; i<JMSProducer.SENNUM;i++){
			//创建一条文本消息
			TextMessage message = session.createTextMessage("发送JMS消息第 " + (i+1) + " 条");
			System.err.println("发消息：ActiveMQ 发送JMS消息 " + (i+1));
			//通过消息生产者发布消息
			messageProducer.send(message);
		}
	}
	
}
