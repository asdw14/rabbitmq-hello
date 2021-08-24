package com.dizhongdi.three;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 * ClassName:Task01
 * Package:com.dizhongdi.two
 * Description:
 *
 * @Date: 2021/8/24 20:20
 * @Author:dizhongdi
 */
public class Task02 {
    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //durable : 让队列持久化
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        for (int i = 0; i < 100; i++) {
            String message =  "RabbitMq"+i;
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("发送消息完成:"+message);
            Thread.sleep(2000);

        }
    }
}
