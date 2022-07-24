package com.dizhongdi.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * ClassName:RabbitMqUtils
 * Package:com.dizhongdi.utils
 * Description:
 *
 * @Date: 2021/8/22 22:12
 * @Author:dizhongdi
 */
public class RabbitMqUtils {
    public static Channel getChannel() throws Exception{
        ConnectionFactory mq = new ConnectionFactory();
        mq.setHost("120.76.118.137");
        mq.setUsername("asdw14");
        mq.setPassword("asdw14");
        Connection connection = mq.newConnection();
        return connection.createChannel();
    }
}
