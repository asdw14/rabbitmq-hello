package com.dizhongdi.five;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

/**
 * ClassName:ReceiveLogs01
 * Package:com.dizhongdi.five
 * Description: 路由模式
 *
 * @Date: 2021/9/14 1:35
 * @Author:dizhongdi
 */
public class ReceiveLogs01 {
    //交换机名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        /**
         * 生成一个临时的队列 队列的名称是随机的
         * 当消费者断开和该队列的连接时 队列自动删除
         */
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,"asdw14");
        System.out.println("等待接收消息,把接收到的消息打印在屏幕.....");
        DeliverCallback deliverCallback = (consumerTag , message) ->{
            String s = new String(message.getBody(), "UTF-8");
            System.out.println("ReceiveLogs01控制台打印接收到的消息"+s);
        };
        channel.basicConsume(queueName,true,deliverCallback, cancelCallback ->{});
    }

}
