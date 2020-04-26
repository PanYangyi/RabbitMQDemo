package com.pyy.rabbitmq.topic;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式
 * 将 路由键 进行 通配符匹配
 * type为 topic
 * @Author panyangyi
 * @create 2020/4/26 21:47
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    private static final String TYPE = "topic";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,TYPE);

        String msg = "商品......";

        channel.basicPublish(EXCHANGE_NAME,"goods.update",null,msg.getBytes());

        System.out.println("----send    :"+msg);

        channel.close();
        connection.close();
    }
}
