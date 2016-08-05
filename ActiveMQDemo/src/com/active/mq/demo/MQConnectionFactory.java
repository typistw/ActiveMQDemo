package com.active.mq.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author weijinsheng
 * @data 2016��7��28�� ����9:40:24
 */
public class MQConnectionFactory {
	
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//Ĭ�������û���
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//Ĭ�����ӵ�ַ
	
	//���乤��
	private static ConnectionFactory connectionFatory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
	
	private static QueueConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEURL);
	
	private static TopicConnectionFactory topicConnectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEURL);
	
	/**
	 * ͨ�����ӹ�����ȡ����
	 * @return
	 */
	public static Connection getConnection(){
		Connection connection = null;
		try {
			connection = connectionFatory.createConnection();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * ͨ�����ӹ�����ȡ����(Queue��ʽ)
	 * @return
	 */
	public static QueueConnection getQueueConnection(){
		QueueConnection connection = null;
		try {
			connection = queueConnectionFactory.createQueueConnection();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * ͨ�����ӹ�����ȡ����(Queue��ʽ:��������)
	 * @return
	 */
	public static TopicConnection getTopicConnection(){
		TopicConnection connection = null;
		try {
			connection = topicConnectionFactory.createTopicConnection();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
