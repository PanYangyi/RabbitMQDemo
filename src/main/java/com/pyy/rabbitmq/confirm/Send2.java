package com.pyy.rabbitmq.confirm;

import com.pyy.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 *
 * confirm----异步确认
 *
 * @Author panyangyi
 * @create 2020/4/28 1:31
 */
public class Send2 {

    private static final String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);



        channel.confirmSelect();    //生产者调用confirmSelect() 将channel设置为confirm模式

        //未确认的消息标识集合
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //添加监听
        channel.addConfirmListener(new ConfirmListener() {

            //没有问题的ACK
            public void handleAck(long l, boolean b) throws IOException {

                if(b){
                    System.out.println("----handleAck----multiple----");
                    confirmSet.headSet(l+1);
                } else {
                    System.out.println("----handleAck----multiple----false");
                    confirmSet.remove(l);
                }
            }

            //有问题的NACK
            public void handleNack(long l, boolean b) throws IOException {
                if(b){
                    System.out.println("----handleNack----multiple----");
                    confirmSet.headSet(l+1);
                } else {
                    System.out.println("----handleNack----multiple----false");
                    confirmSet.remove(l);
                }
            }
        });


        String msg = "hello confirm";

        while (true){

            long nextPublishSeqNo = channel.getNextPublishSeqNo();

            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            confirmSet.add(nextPublishSeqNo);

        }

    }

}
