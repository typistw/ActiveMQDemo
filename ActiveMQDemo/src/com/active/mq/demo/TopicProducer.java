package com.active.mq.demo;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

/**
 * @author weijinsheng
 * @data 2016��7��28�� ����2:26:09
 */
public class TopicProducer {
	
	private static final int SEND_NUM = 10;

    public static void main(String[] args) {
          TopicConnection connection = null;
          TopicSession session = null;
            try {
                // ͨ����������һ������
                connection = MQConnectionFactory.getTopicConnection();
                // ��������
                connection.start();
                // ����һ��session�Ự
                session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
                // ����һ����Ϣ����
                Topic topic = session.createTopic("TopicDemo");
                // ������Ϣ������
                TopicPublisher publisher = session.createPublisher(topic);
                // ���ó־û�ģʽ
                publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                sendMessage(session, publisher);
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

    /**
     * @param session
     * @param publisher
     * @throws Exception
     */
    public static void sendMessage(TopicSession session, TopicPublisher publisher) throws Exception {
        for (int i = 0; i < SEND_NUM; i++) {
            String message = "����Topic��Ϣ��" + (i + 1) + "��";

            MapMessage map = session.createMapMessage();
            map.setString("text", message);
            map.setLong("time", System.currentTimeMillis());
            System.out.println("ActiveMQ ����Topic��Ϣ��"+(i + 1));
            publisher.send(map);
        }
    }
}
