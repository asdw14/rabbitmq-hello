package com.dizhongdi.two;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

/**
 * ClassName:Task01
 * Package:com.dizhongdi.two
 * Description:
 *
 * @Date: 2021/8/22 22:20
 * @Author:dizhongdi
 */
public class Task01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i = 0; i < 100; i++) {
            String message =  "RabbitMq"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息完成:"+message);

        }
    }
}
