package interview;

public class Demo1 {
    public static void main(String[] args) {
        System.out.println(3|9);
        Thread t1 = new Thread(new RunnableDemo());
        Thread t2 = new Thread(new RunnableDemo());
        t1.start();
        t2.start();
    }
}

class RunnableDemo implements Runnable{

    @Override
    public void run() {
//        Singleton singleton = Singleton.getInstance();
        Singleton2 singleton = Singleton2.getInstance();
        System.out.println("该singleton实例的hashcode为:"+singleton.hashCode());
    }
}


/**
 * 单例模式
 */
class Singleton{
    // volatile 保证，当uniqueInstance变量被初始化成Singleton实例时，多个线程可以正确处理uniqueInstance变量
    // 定义一个共享变量来实现通信，它需要是volatile修饰，否则线程不能及时感知

    private  volatile static Singleton uniqueInstance;
    private Singleton(){}

    /**
     * 懒汉模式
     * @return Singleton
     */
    public static Singleton getInstance(){
        if (uniqueInstance==null){
            synchronized (Singleton.class){
                if (uniqueInstance==null){
                    uniqueInstance= new Singleton();
                }
            }
        }
        System.out.println("该实例的hashcode为:"+uniqueInstance.hashCode());
        return uniqueInstance;
    }
}

class Singleton2{
    private static class SingletonHolder{
        private static final Singleton2 INSTANCE = new Singleton2();
    }
    private Singleton2(){};
    public static final Singleton2 getInstance(){
        return SingletonHolder.INSTANCE;
    }
}

