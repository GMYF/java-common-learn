package future;

import java.util.concurrent.*;

public class FutureTaskDemo {
    static Integer count = 0;
    FutureTaskDemo.MyTask task;
    FutureTask<Integer> ft = null;
    class MyTask implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            return count++;
        }
    }
    public static void main(String []args) throws ExecutionException, InterruptedException {
        int count = 0;
        FutureTaskDemo demo = new FutureTaskDemo();
        demo.getTask();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,4,300, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        for(int i=0; i<=5; i++){
            FutureTask<Integer> futureTask = new FutureTask<Integer>(demo.task);
            executor.submit(futureTask);
            if(futureTask.isDone()){
                count += futureTask.get();
            }
        }
        System.out.println("结果集为:"+count);
    }
    public void getTask() throws ExecutionException, InterruptedException {
        task =  new FutureTaskDemo.MyTask();
    }

    interface  Asss {
        int a =1 ;
        static int b =2;

        default void geta(){};
        static void getb(){};
        void getc();
    }
    class AsssImpl implements Asss{
        int c = 0;
        @Override
        public void getc() {
           c =a;
           c =b;
           geta();
           Asss.getb();
        }

    }

}
