package lzz.core.thread.threadsync;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁,可以替换synchronized使用,两者效率差不错,前者功能更强大
 * 并且可以配套Condition使用
 * @author zzli
 */
public class ReentranTestDemo  {

    public static ReentrantLock lock = new ReentrantLock(false);
    // 类似于 wait,notify
    public static final Condition condition = lock.newCondition();
    public static final Condition readCondition = lock.newCondition();
    public static final Condition writeCondition = lock.newCondition();
    public static int i= 0;
    // 取索引
    public static int putIndex= 0;
    // 读索引
    public static int getIndex= 0;
    public static int currentindex= 0;
    // 队列中存在的个数
    public static int count= 0;

    public static Object [] items = new Object[100];
    public static BufferedWriter writer;
    public static BufferedReader reader ;

    static {
        try {
            writer = new BufferedWriter(new FileWriter("d://test.txt"));
            reader = new BufferedReader(new FileReader("d://test.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isWait = true;

    public ReentranTestDemo() throws IOException {
    }

    /**
     * 带等待时间的锁
     */
    static class TryLockRunner implements Runnable{
        @Override
        public void run() {
            try {
                // 在阻塞时间内获取到锁,然后睡眠2秒
                // 别的线程进入后,在1秒中未获取到锁,此时会直接终止等待
                System.out.println(new Date().getSeconds()+"开始");
                // 其实这时多个线程都在同时等待当前的锁释放,所以截图中对应的时间都一样的,
                // 但是持有锁的线程在睡眠2秒,所以过了1秒后,还未获取到锁,所以这些线程都放弃等待结束了
                if(lock.tryLock(1, TimeUnit.SECONDS)){
                    System.out.println("当前线程:["+Thread.currentThread().getName()+"]花费了"+new Date().getSeconds()+"开始锁等待");
                    Thread.currentThread().sleep(2000);
                    System.out.println("当前线程:["+Thread.currentThread().getName()+"]花费了"+new Date().getSeconds()+"秒获取锁成功");
                }else{
                    System.out.println("当前线程:["+Thread.currentThread().getName()+"]花费了"+new Date().getSeconds()+"秒获取锁失败");
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally {

            }

        }
    }

    /**
     * 无等待时间的锁
     */
    static class LockRunner implements Runnable{
        @Override
        public void run() {
            try {
                lock.lock();
                //模拟耗时操作
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName()+"开始睡眠");
                //每个线程只有等当前线程睡眠5秒后,才能获得锁,继续执行
                // lock是阻塞的
            }catch(Exception e){
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        }
    }

    static class ConditionWriteRunner implements  Runnable{

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println("["+Thread.currentThread().getName()+"]已开始");
                // 队列满了,写线程阻塞
                while(items.length==count){
                    System.out.println("["+Thread.currentThread().getName()+"]等待中");
                    writeCondition.await();
                }
                // 耗时操作
                // 创建数组
                currentindex+=50;
                for( ;i<currentindex;i++){
                    //队列满了,让写下标置0
                    if(putIndex==items.length){
                        putIndex=0;
                    }
                    items[putIndex++]= "这是第个"+i+"元素";
                    count++;
                }
                readCondition.signal();
                System.out.println("["+Thread.currentThread().getName()+"]已完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class ConditionReadRunner implements  Runnable{

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println("["+Thread.currentThread().getName()+"]已开始");
                // 空队列不能读
                while(count==0){
                    System.out.println("["+Thread.currentThread().getName()+"]等待中");
                    readCondition.await();
                }
                // 读取操作
//                System.out.println(reader.readLine());
                for(int i = 0 ; i<count ; i++){
                    if(++getIndex==items.length){
                        getIndex=0;
                    }
                    System.out.println(items[getIndex]);
                    count--;
                }
                writeCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println("["+Thread.currentThread().getName()+"]已完成");
        }
    }


    public static void main(String []args) throws InterruptedException {
//        ReentranTestDemo.LockRunner  com.lzz.demo = new ReentranTestDemo.LockRunner();
//        ReentranTestDemo.TryLockRunner  demo2 = new ReentranTestDemo.TryLockRunner();
          ReentranTestDemo.ConditionWriteRunner  writeRunner = new ReentranTestDemo.ConditionWriteRunner();
          ReentranTestDemo.ConditionReadRunner  readRunner = new ReentranTestDemo.ConditionReadRunner();

//        Thread t1 = new Thread(com.lzz.demo);
//        t1.setName("线程1");
//        Thread t2 = new Thread(com.lzz.demo);
//        t2.setName("线程2");
//        t1.start();
//        t2.start();
        for(int i =0 ;i<3; i++){
            Thread thread = new Thread(writeRunner);
//            Thread thread = new Thread(demo2);
            thread.setName("写线程"+i);
            thread.start();
        }
        for(int i =0 ;i<3; i++){
            Thread thread = new Thread(readRunner);
//            Thread thread = new Thread(demo2);
            thread.setName("读线程"+i);
            thread.start();
        }

    }
}
