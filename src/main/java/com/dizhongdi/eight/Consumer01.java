package com.dizhongdi.eight;

import com.dizhongdi.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:Consumer01
 * Package:com.dizhongdi.eight
 * Description:
 *  死信交换机
 *  消费者C1
 * @Date: 2021/9/14 3:06
 * @Author:dizhongdi
 */
public class Consumer01 {
    //普通交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    //死信交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";
    //普通队列
    public static final String NORMAL_QUEUE= "normal_queue";
    //死信队列
    public static final String DEAD_QUEUE= "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //正常交换机和队列
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.TOPIC);
        //正常队列绑定死信队列信息
        Map<String, Object> params = new HashMap<>();
        //正常队列设置死信交换机 参数 key 是固定值
        params.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //正常队列设置死信 routing-key 参数 key 是固定值
        params.put("x-dead-letter-routing-key", "lisi");
        //设置正常队列长度的限制
//        params.put("x-max-length",6);
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,params);
        //死信交换机和队列
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        DeliverCallback deliverCallback = (consumerTag , message) ->{
            String s = new String(message.getBody(), "UTF-8");
            if (s.equals("info5")){
                System.out.println("Consumer01 接收到消息" + s + "并拒绝签收该消息");
                //requeue 设置为 false 代表拒绝重新入队 该队列如果配置了死信交换机将发送到死信队列中
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            }else {
                System.out.println("Consumer01 接收到消息"+s);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback, cancelCallback ->{});


    }
}
