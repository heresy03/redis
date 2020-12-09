package com.cx.demo02.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask01 {

    // 定义队列名称
    private final static String QUEUE_NAME = "queue-task";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            // 声明为true
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            String message = "Fifth message......................................";

            channel.basicPublish("", QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}
