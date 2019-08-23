package threadsync;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    public static class lockSupportRunner implements Runnable{
        @Override
        public void run() {
            System.out.println("X");
            LockSupport.park();
        }
    }

    public static void main (String [] args){
        LockSupportDemo.lockSupportRunner runner = new LockSupportDemo.lockSupportRunner();
        Thread t = new Thread(runner);
        t.start();
        System.out.println(LockSupport.getBlocker(t));
        LockSupport.unpark(t);
    }
}
