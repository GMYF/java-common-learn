package demo;

import org.junit.Test;
import threadfactory.NamefulThreadFactory;
import uncatchexception.UncaughtException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

/**
 * 用多线程模拟卖票
 * @author zz.li
 */
public class SellTicketsDemo {
    /**
     *定义票数
     */
    static int tickets = 500;
    /**
     *定义锁
     */
    static Object obj= new Object();
    /**
     *定义线程工厂
     */
    NamefulThreadFactory factory = new NamefulThreadFactory("卖票窗口");
    /**
     *定义自定义异常处理
     */
    UncaughtException exception = new UncaughtException();
    /**
     *默认的处理机制
     */
    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
    /**
     *定义任务队列,用来存放待处理的任务,容量为10
     */
    BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
    /**
     *定义处理时间类型
     */
    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    /**
     *定义线程池
     */
    ScheduledThreadPoolExecutor executor1 = new ScheduledThreadPoolExecutor(3,new NamefulThreadFactory("检测余票线程"), exception);
    ThreadPoolExecutor executor = new ThreadPoolExecutor(4,4,300 ,
            timeUnit, queue, factory,handler);
    static boolean ticketsFlag = false;
    /**
     * 卖票的任务
     */
    class SellTicketJob implements Runnable{
        public String sellAddress = "";
        public SellTicketJob(String name){
            sellAddress = name;
        }
        @Override
        public void run() {
            //方式一:用synchronized实现锁
            int count= 0;
            while (tickets>0){
                Calendar calendar = Calendar.getInstance();
                Long time1 = calendar.getTimeInMillis();
                do {
                    synchronized (obj) {
                        //对票进行锁定
                        tickets--;
                        count++;
                        System.out.println(sellAddress + Thread.currentThread().getName() + "出票成功");
                    }
                }while(count<=2);
                calendar = Calendar.getInstance();
                long time2 = calendar.getTimeInMillis();
                System.out.println(sellAddress + Thread.currentThread().getName() + "出票总用时:"+(time2-time1));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CheckTicketsJob implements Runnable{
        @Override
        public void run() {
            System.out.println( Thread.currentThread().getName()+"检测当前票数为:"+tickets);
            if(tickets==0){
               ticketsFlag =true;
            }
        }
    }

    @Test
    public void test(){
        String [] address = {"上海","北京","武汉","重庆","广州","深圳","宜昌"
               };
        sellWindow(address);
    }

    public static void main(String[]args){
        String [] address = {"上海","北京","武汉","重庆","广州","深圳","宜昌"};
        SellTicketsDemo demo = new SellTicketsDemo();
        demo.sellWindow(address);
    }

    public  void sellWindow(String [] windows){
        executor1.scheduleAtFixedRate(new CheckTicketsJob(),10,10,TimeUnit.MILLISECONDS);
        List<SellTicketsDemo.SellTicketJob > jobList = new ArrayList<>();
        for(int i=0;i<windows.length;i++){
            SellTicketJob  job = new SellTicketJob(windows[i]);
            jobList.add(job);
        }
        for(int i=0;i<jobList.size();i++){
            executor.execute(jobList.get(i));
            System.out.println( "阻塞队列的数量"+queue.size()+"线程池中处理线程的数量:"+executor.getActiveCount());
        }
        while (!ticketsFlag){
            if((!executor.isShutdown())){
                executor.shutdown();
            }
        }
    }
}
