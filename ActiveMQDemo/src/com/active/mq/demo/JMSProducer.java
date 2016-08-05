package com.active.mq.demo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author weijinsheng
 * @data 2016��7��28�� ����10:06:40
 */
public class JMSProducer {
	
	private static final int SENNUM = 10;//������Ϣ������
	
	public static void main(String[] args) {
		Connection connection = null;
		Session session = null;
		Destination destination;
		//��Ϣ������
		MessageProducer messageProducer;
		
		try {
			connection = MQConnectionFactory.getConnection();
			connection.start();
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			//����һ������Ϊ HelloMQ ����Ϣ����
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
			//����һ���ı���Ϣ
			TextMessage message = session.createTextMessage("����JMS��Ϣ�� " + (i+1) + " ��");
			System.err.println("����Ϣ��ActiveMQ ����JMS��Ϣ " + (i+1));
			//ͨ����Ϣ�����߷�����Ϣ
			messageProducer.send(message);
		}
	}
	
}
