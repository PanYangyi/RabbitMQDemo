package com.pyy.rabbitmq.simplemq;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单队列模式
 * 消息的生产者
 * @Author panyangyi
 * @create 2020/4/21 23:33
 */
public class Send {


    private static final String QUEUE_NAME = "test_queue_1";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //创建队列申明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //消息内容
        String msg = "hello world rabbitmq";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("[publish msg :] "+msg);

        channel.close();
        connection.close();
    }
}
