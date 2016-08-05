package com.active.mq.demo;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author weijinsheng
 * @data 2016��7��28�� ����9:52:37
 */
public class JMSConsumer {
	
	public static void main(String[] args) {
		Connection connection = null;
		Session session = null;//�Ự�����ܻ��߷�����Ϣ�߳�
		Destination destination;//��ϢĿ�ĵ�
		MessageConsumer messageConsumer;//��Ϣ��������
		
		try {
			connection = MQConnectionFactory.getConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//����һ������HelloMQ����Ϣ����
			destination = session.createQueue("HelloMQ");
			//������Ϣ������
			messageConsumer = session.createConsumer(destination);
			
			while(true){
				TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
				if(textMessage != null){
					System.out.println("�յ�����Ϣ��" + textMessage.getText());
				}else{
					break;
				}
			}
			//�ύ�Ự
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
