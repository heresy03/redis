package com.cx.demo03;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 声明交换机，禁止发布到不存在的Exchange
            // 如果没有队列绑定到交换机，消息将丢失，但对我们来说没问题；如果没有用户在监听，我们可以安全地丢弃消息
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message =  "debug:Hello World!" ;

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
