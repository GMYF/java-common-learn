package threadlocal;

/**
 * @author zz.li
 * @see ThreadLocalDemo
 */
public class ThreadLocalDemo {
    /**
     * Theadlocal用来区分线程间的变量,线程是跨多个函数的,那么这些线程之间的变量是如何共享数据的,
     * 可以用Theadlocal来管理每个线程的变量,从而对不同线程之间的变量产生隔离
     * 为什么要使用private static 来修饰ThreadLocal,防止ThreadLocalMap的key因为弱引用产生内存泄漏的问题
     */
    private static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>(){
        @Override
        protected Object initialValue()
        {
            System.out.println("调用get方法时，当前线程共享变量没有设置，调用initialValue获取默认值！");
            return null;
        }
    };

    static class CalculateJob implements  Runnable{
        private String name;
        public CalculateJob(String name){
            this.name = name;
        }

        @Override
        public void run() {
            for(int i=0;i<5;i++){
                if(ThreadLocalDemo.threadLocal.get()==null){
                    ThreadLocalDemo.threadLocal.set(0);
                    System.out.println("线程["+name+"]默认初始化的变量为0");
                }else{
                    int num = (int) ThreadLocalDemo.threadLocal.get();
                    ThreadLocalDemo.threadLocal.set(num+1);
                    System.out.println("线程["+name+"]:"+ThreadLocalDemo.threadLocal.get());
                    if(i==3){
                        ThreadLocalDemo.threadLocal.remove();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main (String []args){
        Thread t = new Thread(new ThreadLocalDemo.CalculateJob("name1"));
        Thread t2 = new Thread(new ThreadLocalDemo.CalculateJob("name2"));
        Thread t3 = new Thread(new ThreadLocalDemo.CalculateJob("name3"));
        t.start();
        t2.start();
        t3.start();
        try{
            t.join();
            t2.join();
            t3.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
