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
 * @data 2016年7月28日 上午9:40:24
 */
public class MQConnectionFactory {
	
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认连接用户名
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认链接地址
	
	//发射工厂
	private static ConnectionFactory connectionFatory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
	
	private static QueueConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEURL);
	
	private static TopicConnectionFactory topicConnectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEURL);
	
	/**
	 * 通过连接工厂获取连接
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
	 * 通过连接工厂获取连接(Queue方式)
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
	 * 通过连接工厂获取连接(Queue方式:发布订阅)
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
