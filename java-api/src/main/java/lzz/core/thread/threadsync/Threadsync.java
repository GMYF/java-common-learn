package lzz.core.thread.threadsync;

import java.util.HashMap;
import java.util.Map;

public class Threadsync {
    static Map<String,String> map =new HashMap<>();
    public static class AddTread  implements Runnable{
        Integer count = 0;
        public AddTread(Integer count){
            this.count =count;
        }
        @Override
        public void run() {
            for(int i=0 ; i<100000 ; i++){
                map.put(Integer.toString(i),Integer.toBinaryString(i));
            }
        }
    }
    public static void main (String [] args) throws InterruptedException{
//        Thread  thread = new Thread(new Threadsync.AddTread(0));
//        Thread  thread2 = new Thread(new Threadsync.AddTread(0));
//        thread.start();
//        thread2.start();
//        thread2.join();
//        // 调用线程必须等被调用线程执行完后,才继续执行
//        // 也就是说父线程必须等子线程执行完了后才能继续执行
//        thread.join();
//        thread.wait();
//        System.out.println(map.size());
        System.out.println((int)'·');
    }
}
