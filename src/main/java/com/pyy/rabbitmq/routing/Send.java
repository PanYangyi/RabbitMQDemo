package com.pyy.rabbitmq.routing;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.pyy.rabbitmq.util.User;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式
 *
 * 路由模式的type是direct
 * @Author panyangyi
 * @create 2020/4/26 1:47
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    private static final String TYPE = "direct";

    private static final String ROUTINGKEY = "key1";    //路由键

    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接
        Connection connection = ConnectionUtils.getConnection();

        //创建通道
        Channel channel = connection.createChannel();

        //exchange
        channel.exchangeDeclare(EXCHANGE_NAME,TYPE);

        User user = new User("U123456", "Jack");

        channel.basicPublish(EXCHANGE_NAME,ROUTINGKEY,null,user.toString().getBytes("utf-8"));

        channel.close();

        connection.close();


    }

}
