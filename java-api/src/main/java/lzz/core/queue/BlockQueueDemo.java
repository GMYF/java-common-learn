package lzz.core.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockQueueDemo {
    private static LinkedBlockingQueue queue = new LinkedBlockingQueue<>(40);

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    // 生产内容
                    // 队尾添加一条数据，若队列已满，抛出异常
                    queue.add("生产了消费内容" + i);
                    // 一直阻塞，知道队列可用
                    // queue.put();
                    // 队列可用，生产内容，返回true，队列不可用，返回false
                    // queue.offer()
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    // 生产内容
                    // 队列已满，抛出异常
                    try {
                        // 获取队头的元素，并不删除
                        String msg = (String) queue.peek();
                        System.out.println("消费的内容为：" + msg);
                        // 删除队头第一个元素.队列为空，返回null
                        queue.poll(10, TimeUnit.SECONDS);
                        // 删除队头第一个元素.队列为空，返回异常
//                        queue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 一直阻塞，知道队列可用
                    // queue.put();
                    // 队列可用，生产内容，返回true，队列不可用，返回false
                    // queue.offer()
                }
            }
        }).start();
    }
}
