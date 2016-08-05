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
 * ��Ϣ������
 * @author weijinsheng
 * @data 2016��7��28�� ����11:02:31
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
			//����һ����Ϣ����
			Queue queue = queueSession.createQueue("QueueMsgDemo");
			//������Ϣ������
			QueueSender sender = queueSession.createSender(queue);
			//���ó־�ģʽ
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
			String message = "����Queue��Ϣ���� " + (i+1) + "��";
			MapMessage map = session.createMapMessage();
			map.setString("text", message);
			map.setLong("time", System.currentTimeMillis());
			System.out.println("Active����Queue��Ϣ��" + (i+1));
			sendr.send(map);
		}
	}
}
