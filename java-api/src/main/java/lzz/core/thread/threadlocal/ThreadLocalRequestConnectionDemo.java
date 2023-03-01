package lzz.core.thread.threadlocal;

public class ThreadLocalRequestConnectionDemo {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(){
        @Override
        protected Connection initialValue() {
            System.out.println("当前线程" + Thread.currentThread().getName() + "的threadLocal已被初始化");
            return new Connection("线程池连接");
        }
    };
    public static class Connection {
        public Connection(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++ ) {
            new Thread(()->{
                if (ThreadLocalRequestConnectionDemo.threadLocal.get() == null) {
                    System.out.println("当前线程" + Thread.currentThread().getName() + "未获取到连接");
                }else {
                    System.out.println("获取到" + ThreadLocalRequestConnectionDemo.threadLocal.get());
                }
            }).start();
        }
    }
}
