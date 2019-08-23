package threadpool;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorsDemo {
    public static ThreadPoolExecutor executor =  new ThreadPoolExecutor(1,1,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(){});

    public static ThreadPoolExecutor executor2 =  new ThreadPoolExecutor(5,10,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());

    public static ThreadPoolExecutor executor3 =  new ThreadPoolExecutor(5,10,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()){
        //对ThreadPoolExecutor的内部实现
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("准备执行"+((DoubleThreadPoolDemo)r).name);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("执行完成"+((DoubleThreadPoolDemo)r).name);
        }

        @Override
        protected void terminated() {
            System.out.println("线程池推出,当前系统中可用的线程数量为:"+Runtime.getRuntime().availableProcessors());
        }
    };


    public static class SingleThreadPoolDemo implements Runnable{
        // 重入锁
        final ReentrantLock lock = new ReentrantLock();
        // 类似于 wait,notify
        final Condition condition = lock.newCondition();
        int count = 0;
        public SingleThreadPoolDemo(int count){
            this.count = count;
        }

        @Override
        public void run() {
            int j = 0;
            while (j<15){
                j++;
                try{
                    lock.lockInterruptibly();//带有中断的加锁
                    if (j%count==0){
                        Thread.sleep(500);
                    }
                    if(lock.tryLock(1,TimeUnit.SECONDS)){
                        System.out.println("当前线程["+Thread.currentThread().getName()+"]输出的值为:"+j);
                    }else{
                        condition.signal();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
            System.out.println("当前时间为:"+new Date().toString());
        }
    }

    public static class DoubleThreadPoolDemo implements Runnable{
        // 重入锁
        final ReentrantLock lock = new ReentrantLock();
        // 类似于 wait,notify
        final Condition condition = lock.newCondition();
        int count = 0;
        public String name;
        public void myTask(String name){
            this.name =name;
        }
        public DoubleThreadPoolDemo(int count,String name){
            this.name = name;
            this.count = count;
        }

        @Override
        public void run() {
            int j = 0;
            while (j<15){
                j++;
                try{
//                    lock.lockInterruptibly();//带有中断的加锁
                    if (j%count==0){
                        Thread.sleep(1500);
                    }
                    if(lock.tryLock(1,TimeUnit.SECONDS)){
                        System.out.println("当前多线程工厂中的线程["+Thread.currentThread().getName()+"]输出的值为:"+j);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                    if(Thread.currentThread().isInterrupted()){
                        Thread.currentThread().interrupt();
                    }
                }
            }
//            System.out.println("当前时间为:"+new Date().toString());
        }
    }


    public static void main (String []args){
//        ExecutorsDemo.SingleThreadPoolDemo demo  = new ExecutorsDemo.SingleThreadPoolDemo(2);
//        executor.execute(demo);
//        executor.shutdown();
        // 如果在for循环内对lock对象,那么每个线程线程所持有的lock是独立的,各线程中不会发生lock阻塞的情况
        ExecutorsDemo.DoubleThreadPoolDemo demo2  = new ExecutorsDemo.DoubleThreadPoolDemo(2,"多线程工厂");
        //多次添加任务
        for(int i=1;i<=5;i++){
            // execute 会抛出异常,submit不会
            executor3.execute(demo2);
        }
        executor3.shutdown();
    }
}
