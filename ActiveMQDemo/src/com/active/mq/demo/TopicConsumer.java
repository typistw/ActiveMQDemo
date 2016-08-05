package com.active.mq.demo;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

/**
 * @author weijinsheng
 * @data 2016��7��28�� ����2:37:16
 */
public class TopicConsumer {

	public static void main(String[] args) {
		TopicConnection connection = null;
		TopicSession session = null;
		try {
			// ͨ����������һ������
			connection = MQConnectionFactory.getTopicConnection();
			// ��������
			connection.start();
			// ����һ��session�Ự
			session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// ����һ����Ϣ����
			Topic topic = session.createTopic("TopicDemo");
			// ������Ϣ������
			TopicSubscriber subscriber = session.createSubscriber(topic);

			subscriber.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message msg) {
					if (msg != null) {
						MapMessage map = (MapMessage) msg;
						try {
							System.out.println(map.getLong("time") + "Topic������Ϣ#" + map.getString("text"));
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}
			});

			// ����100ms�ٹر�
			Thread.sleep(1000 * 100);
			// �ύ�Ự
			session.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// �ر��ͷ���Դ
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
