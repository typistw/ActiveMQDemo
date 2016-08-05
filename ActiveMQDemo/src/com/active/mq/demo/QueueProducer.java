package com.active.mq.demo;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

/**
 * 消息生产者
 * @author weijinsheng
 * @data 2016年7月28日 上午11:02:31
 */
public class QueueProducer {

	private static final int SEND_NUM = 10;
	
	public static void main(String[] args) {
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		
		try {
			queueConnection = MQConnectionFactory.getQueueConnection();
			queueConnection.start();
			queueSession = queueConnection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//创建一个消息队列
			Queue queue = queueSession.createQueue("QueueMsgDemo");
			//创建消息发送者
			QueueSender sender = queueSession.createSender(queue);
			//设置持久模式
			sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			sendMessage(queueSession, sender);
			queueSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(queueSession != null){
				try {
					queueSession.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
			if(queueConnection != null){
				try {
					queueConnection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param session
	 * @param sendr
	 * @throws Exception
	 */
	public static void sendMessage(QueueSession session,QueueSender sendr) throws Exception{
		for(int i=0; i<SEND_NUM; i++){
			String message = "发送Queue消息，第 " + (i+1) + "条";
			MapMessage map = session.createMapMessage();
			map.setString("text", message);
			map.setLong("time", System.currentTimeMillis());
			System.out.println("Active发送Queue消息：" + (i+1));
			sendr.send(map);
		}
	}
}
