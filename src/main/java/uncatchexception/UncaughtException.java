package uncatchexception;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义处理线程池异常
 * AbortPolicy  线程池队列满了丢掉这个任务,抛出RejectedExecutionException异常
 * DiscardPolicy 如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常。
 * DiscardOldestPolicy 也就是说如果队列满了，会将最早进入队列的任务,再尝试加入队列。
 * CallerRunsPolicy 如果添加到线程池失败，那么主线程会自己去执行该任务，
 * 自定义
 */
public class UncaughtException implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.getClass());
    }
}
