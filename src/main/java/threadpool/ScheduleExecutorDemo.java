package threadpool;

import runnable.TestRunable;
import threadfactory.NamefulThreadFactory;
import uncatchexception.UncaughtException;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author zz.li
 * @apiNote 定时任务.用这个可以抛弃timer定时器了, 与timer的区别请参考
 *  * 1:timer是单线程,某任务阻塞后,整个程序就终止了
 *  * 2:Timer线程并不捕获异常,ScheduledExecutorService 可以自定义异常
 *  * 3:Timer是依据于绝对时间的,若客户端的时间和服务器端的时间不一致,这个会有问题,ScheduledExecutorService是相对时间
 *
 */
public class ScheduleExecutorDemo {
    private final TestRunable testRunable = new TestRunable();
    public static ScheduledExecutorService  scheduledExecutorService = new ScheduledThreadPoolExecutor(3,
            new NamefulThreadFactory("测试线程"),new UncaughtException());

    /**
     * 延迟30ms执行,并只执行一次
     */
    public void startThreadWithDelay(){
        System.out.println("线程于"+ new Date().toLocaleString() +"开始运行");
        scheduledExecutorService.schedule(testRunable,30,TimeUnit.MILLISECONDS);
    }

    /**
     *多次执行
     */
    public void startThreadWithInitDelay(){
        System.out.println("线程于"+ new Date().toLocaleString() +"开始运行");
        scheduledExecutorService.scheduleAtFixedRate(testRunable,1,2,TimeUnit.SECONDS);
    }

    public static void main(String []args){
        ScheduleExecutorDemo demo = new ScheduleExecutorDemo();
        demo.startThreadWithDelay();
//        demo.startThreadWithInitDelay();
//        scheduledExecutorService.shutdown();
    }
}
