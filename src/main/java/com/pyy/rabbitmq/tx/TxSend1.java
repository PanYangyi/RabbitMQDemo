package com.pyy.rabbitmq.tx;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * 事务机制(两种)
 *              1、AMQP协议实现事务
 *
 *                  channel.txSelect();
 *                  channel.txCommit();
 *                  channel.txRollback();
 *
 *                      缺点：降低了消息的吞吐量
 *
 *              2、comfirm模式
 *
 *
 * @Author panyangyi
 * @create 2020/4/27 23:01
 */
public class TxSend1 {

    private static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello tx";

        try {
            channel.txSelect();     //开启事务

            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            int i = 1/0;

            channel.txCommit();     //提交事务

            System.out.println("send message success");
        } catch (IOException e) {

            channel.txRollback();   //回滚事务

            System.out.println("send message fail");
        }   finally {
            //关闭资源
            channel.close();
            connection.close();
        }


    }

}
