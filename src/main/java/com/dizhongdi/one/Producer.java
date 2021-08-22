package com.dizhongdi.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:Producer
 * Package:com.dizhongdi
 * Description:
 *
 * @Date: 2021/8/22 21:26
 * @Author:dizhongdi
 */
public class Producer {
    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory mq = new ConnectionFactory();
        mq.setHost("120.25.77.162");
        mq.setUsername("asdw14");
        mq.setPassword("asdw14");
        Connection connection = mq.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "hello message";
        channel.basicPublish("", QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕！");


    }
}
