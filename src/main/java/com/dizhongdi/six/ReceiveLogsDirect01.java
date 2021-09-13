package com.dizhongdi.six;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * ClassName:ReceiveLogsDirect01
 * Package:com.dizhongdi.six
 * Description:
 *      发布订阅模式
 * @Date: 2021/9/14 2:06
 * @Author:dizhongdi
 */
public class ReceiveLogsDirect01 {
    //交换机名称
    public static final String EXCHANGE_NAME = "direct_logs";
    public static final String QUEUE_NAME = "console";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"info");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"warning");
        DeliverCallback deliverCallback = (consumerTag , message) ->{
            String s = new String(message.getBody(), "UTF-8");
            System.out.println("ReceiveLogsDirect01控制台打印接收到的消息"+s);
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback, cancelCallback ->{});
    }

}
