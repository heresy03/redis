package com.cx.demo01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
    // 定义队列名称
    private final static String QUEUE_NAME = "hello-demo1";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try ( // 创建一个到服务的连接，socket连接的抽象,为我们负责协议版本协商和认证等工作。
                // 连接到本地主机代理，如果要连接到不同机器的代理，需要此处指定他的名字或者IP地址
                Connection connection = factory.newConnection();
                //创建一个channel，包含获取数据的大部分api，包含在try语句中，因为
                //不管是Connection还是Channel都实现了Closeable接口，这样我们不需要明确的在代码中关闭他们
                Channel channel = connection.createChannel()) {
            // 声明一个队列用于发送数据，如果队列不存在则创建，消息内容是字节数组，可以根据需要对其进行编码
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            // 发布一条消息到队列中
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
