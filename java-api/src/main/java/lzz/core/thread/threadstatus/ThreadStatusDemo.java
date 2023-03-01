package lzz.core.thread.threadstatus;

public class ThreadStatusDemo {
    private Object o = new Object();
    public static class SometimeWaitingThread implements Runnable {
        @Override
        public void run () {
            // 自身加锁，并等待，别人唤醒
            while (true) {
                synchronized (SometimeWaitingThread.class) {
                    try {
                        SometimeWaitingThread.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("SometimeWaitingThread线程，我一直在等待，谁能唤醒我么？");
            }
        }
    }

    public static class OnceWaitingThread implements Runnable {
        @Override
        public void run () {
            // 自身加锁，并等待，别人唤醒
            synchronized (OnceWaitingThread.class) {
                try {
                    OnceWaitingThread.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("SometimeWaitingThread线程，我曾经在等待，谁能唤醒我么？");
        }
    }

    public static class AlwaysWaitingThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                // 一直睡眠，不会被唤醒
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("AlwaysWaitingThread，我使劲的睡？");
            }
        }
    }

    public static class AlwaysBlockThread implements Runnable{

        @Override
        public void run() {
            synchronized (AlwaysBlockThread.class) {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class someTimeBlockThread implements Runnable{

        @Override
        public void run() {
            synchronized (AlwaysBlockThread.class) {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadStatusDemo demo = new ThreadStatusDemo();
//        new Thread(new OnceWaitingThread()).start();
////        new Thread(new OnceWaitingThread()).start();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        OnceWaitingThread.class.notifyAll();
        new Thread(()->{
            try {
                synchronized (demo.o) {
                    demo.o.wait();
                    System.out.println("xxx");
                }
                System.out.println("不用等待了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            synchronized (demo.o) {
                demo.o.notify();
            }
            System.out.println("通知OnceWaitingThread，不用等待了");
        }).start();

    }

}
