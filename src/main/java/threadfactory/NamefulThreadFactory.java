package threadfactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池
 * @author zzli
 */
public class NamefulThreadFactory implements ThreadFactory {
    public final AtomicInteger threadNumber =new AtomicInteger(1);
    public String threadName;
    public NamefulThreadFactory(String threadName){
        this.threadName =threadName+"-";
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r,threadName+threadNumber.getAndIncrement());
        if(t.isDaemon()){
            t.setDaemon(true);
        }
        if(t.getPriority()!=Thread.NORM_PRIORITY){
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
