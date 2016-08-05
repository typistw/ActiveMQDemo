package com.active.mq.demo;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

/**
 * @author weijinsheng
 * @data 2016年7月28日 上午11:37:06
 */
public class QueueConsumer {
	
	public static void main(String[] args) {
		QueueConnection queueConnection = null;
		QueueSession queueSession = null;
		
		try {
			queueConnection = MQConnectionFactory.getQueueConnection();
			queueConnection.start();
			queueSession = queueConnection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			Queue queue = queueSession.createQueue("QueueMsgDemo");
			//创建消息接收者
			QueueReceiver receiver = queueSession.createReceiver(queue);
			
			receiver.setMessageListener(new MessageListener() {
				
				public void onMessage(Message msg) {
					if(msg != null){
						MapMessage map = (MapMessage) msg;
						try {
							System.out.println(map.getLong("time") + " 接收到消息#" + map.getString("text"));
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}
			});
			
			Thread.sleep(1000 * 100);
			
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
}
