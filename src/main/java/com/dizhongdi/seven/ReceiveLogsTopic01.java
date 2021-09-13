package com.dizhongdi.seven;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * ClassName:ReceiveLogsTopic01
 * Package:com.dizhongdi.seven
 * Description: 声明主题交换机 及相关队列
 *
 *      消费者C1
 *
 * @Date: 2021/9/14 2:37
 * @Author:dizhongdi
 */
public class ReceiveLogsTopic01 {
    //交换机名称
    public static final String EXCHANGE_NAME = "topic_logs";
    public static final String QUEUE_NAME = "Q1";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.TOPIC);
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"*.orange.*");

        DeliverCallback deliverCallback = (consumerTag , message) ->{
            String s = new String(message.getBody(), "UTF-8");
            System.out.println("接收队列 :  "+QUEUE_NAME + "绑定键 ：" + message.getEnvelope().getRoutingKey() + "消息: " +s);
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback, cancelCallback ->{});
    }
}
