package com.pyy.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.scenario.effect.impl.prism.PrImage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 获取mq的连接
 * @Author panyangyi
 * @create 2020/4/21 23:09
 */
public class ConnectionUtils {

    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 5672;
    private static final String USER_NAME = "user_pyy";
    private static final String PASS_WORD = "123456";
    private static final String VIRTUAL_HOST = "/vhost_pyy";


    public static Connection getConnection() throws IOException, TimeoutException {

        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(PORT);
        connectionFactory.setPassword(PASS_WORD);
        connectionFactory.setUsername(USER_NAME);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);

        Connection connection = connectionFactory.newConnection();

        return connection;
    }
}
