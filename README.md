#开发环境

我使用的是ActiveMQ 5.11.2 Release的Windows版，官网最新版是ActiveMQ 5.13.4 Release。[下载地址](http://activemq.apache.org/download.html)

需要注意的是，开发时候，要将apache-activemq-5.11.2-bin.zip解压缩后里面的activemq-all-5.11.2.jar包加入到classpath下面，这个包包含了所有jms接口api的实现。

##ActiviteMQ消息有3中形式

JMS 公共 ----------点对点域 ----------发布/订阅域

ConnectionFactory ---------- QueueConnectionFactory ---------- TopicConnectionFactory

Connection ---------- QueueConnection ---------- TopicConnection

Destination ---------- Queue ---------- Topic

Session ---------- QueueSession ---------- TopicSession

MessageProducer ---------- QueueSender ---------- TopicPublisher

MessageConsumer ---------- QueueReceiver ---------- TopicSubscriber

###(1)、点对点方式（point-to-point）

点对点的消息发送方式主要建立在 Message Queue,Sender,reciever上，Message Queue 存贮消息，Sneder 发送消息，receive接收消息.具体点就是Sender Client发送Message Queue ,而 receiver Cliernt从Queue中接收消息和"发送消息已接受"到Quere,确认消息接收。消息发送客户端与接收客户端没有时间上的依赖，发送客户端可以在任何时刻发送信息到Queue，而不需要知道接收客户端是不是在运行

###(2)、发布/订阅 方式（publish/subscriber Messaging）

发布/订阅方式用于多接收客户端的方式.作为发布订阅的方式，可能存在多个接收客户端，并且接收端客户端与发送客户端存在时间上的依赖。一个接收端只能接收他创建以后发送客户端发送的信息。作为subscriber ,在接收消息时有两种方法，destination的receive方法，和实现message listener 接口的onMessage 方法。

####ActiviteMQ接收和发送消息基本流程
![](https://github.com/typistw/ActiveMQDemo/blob/master/ActiveMQDemo/resources/flow.png)  

####发送消息的基本步骤：

(1)、创建连接使用的工厂类JMS ConnectionFactory

(2)、使用管理对象JMS ConnectionFactory建立连接Connection，并启动

(3)、使用连接Connection 建立会话Session

(4)、使用会话Session和管理对象Destination创建消息生产者MessageSender

(5)、使用消息生产者MessageSender发送消息

消息接收者从JMS接受消息的步骤

(1)、创建连接使用的工厂类JMS ConnectionFactory

(2)、使用管理对象JMS ConnectionFactory建立连接Connection，并启动

(3)、使用连接Connection 建立会话Session

(4)、使用会话Session和管理对象Destination创建消息接收者MessageReceiver

(5)、使用消息接收者MessageReceiver接受消息，需要用setMessageListener将MessageListener接口绑定到MessageReceiver消息接收者必须实现了MessageListener接口，需要定义onMessage事件方法。

#运行
1.启动activemq服务器<br>
2.登录服务器:http://127.0.0.1:8161/admin, 默认用户名、密码:admin<br>
3.运行消息生产者(查看Active服务器)<br>
4.运行消息消费者（查看Active服务器）<br>
