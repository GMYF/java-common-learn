package lzz.core.thread.threadsync;

import java.util.concurrent.locks.ReentrantLock;

public class FairLock  implements  Runnable{
    // 使用公平锁
    public  static ReentrantLock lock = new ReentrantLock(false);

    @Override
    public void run() {
        while (true){
            try{
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"开始运行:");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    public static void main(String [] args){
        FairLock fair = new FairLock();
        Thread t1 = new Thread(fair);
        Thread t2 = new Thread(fair);
        t1.setName("线程1");
        t2.setName("线程2");
        t1.start();
        t2.start();
    }
}
