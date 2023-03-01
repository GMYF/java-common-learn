package lzz.core.thread.threadsync;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLock implements Runnable{
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();

    int lock;
    public DeadLock(int lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        try{
            if(lock==1){
                lock1.lockInterruptibly();// 以可以响应中断的方式加锁
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock2.lockInterruptibly();
            }else{
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock1.lockInterruptibly();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            if(lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if(lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId()+":"+Thread.currentThread().getName()+"退出");
        }
    }
    public static void main (String []args){
        DeadLock r1 = new DeadLock(1);
        DeadLock r2 = new DeadLock(2);
        Thread th1= new Thread(r1);
        Thread th2 = new Thread(r2);
        th1.setName("线程1");
        th2.setName("线程2");
        th1.start();
        th2.start();
        try{
        Thread.sleep(2000);
        th2.interrupt();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
