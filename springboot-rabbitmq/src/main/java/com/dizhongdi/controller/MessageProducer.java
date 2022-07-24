package com.dizhongdi.controller;

import com.dizhongdi.callback.MyCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * ClassName:MessageProducer
 * Package:com.dizhongdi.controller
 * Description:
 *      消息回退
 * @Date: 2022/7/24 23:40
 * @Author:dizhongdi
 */
@Slf4j
@RestController
@RequestMapping("/return")
public class MessageProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    MyCallBack myCallBack;
    //rabbitTemplate 注入之后就设置该值
    @PostConstruct
    private void init() {
        rabbitTemplate.setConfirmCallback(myCallBack);
        /**
         * true：
         * 交换机无法将消息进行路由时，会将该消息返回给生产者
         * false：
         * 如果发现消息无法进行路由，则直接丢弃
         */
        rabbitTemplate.setMandatory(true);
        //设置回退消息交给谁处理
        rabbitTemplate.setReturnCallback(myCallBack);
    }
    @GetMapping("sendMessage/{message}")
    public void sendMessage(@PathVariable String message){
        //让消息绑定一个 id 值
        CorrelationData correlationData1 = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("confirm.exchange","key1",message+"key1",correlationData1)
        ;
        log.info("发送消息 id 为:{}内容为{}",correlationData1.getId(),message+"key1");
        CorrelationData correlationData2 = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("confirm.exchange","key2",message+"key2",correlationData2)
        ;
        log.info("发送消息 id 为:{}内容为{}",correlationData2.getId(),message+"key2");
    }

}
