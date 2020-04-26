package com.pyy.rabbitmq.ps;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布/订阅模式
 * @Author panyangyi
 * @create 2020/4/22 15:37
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //定义交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String msg = "hello world";

        //往交换机里发送消息
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        channel.close();
        connection.close();
    }



}
