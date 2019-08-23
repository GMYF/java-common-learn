package framework.fork;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTackDemo  extends RecursiveTask<Long> {

    private static final int THRESHOLD =10000;
    private  long start;
    private  long end;

    public CountTackDemo(long start,long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum =0;
        boolean canCompute = (end-start)<THRESHOLD;
        if(canCompute){
            for(long i =start;i<end;i++){
                sum+=i;
            }
        }else{
            long step = (start+end)/10000000;
            ArrayList<CountTackDemo> subTasks = new ArrayList<CountTackDemo>();
            long pos = start;
            for(int i=0;i<10000000;i++){
                long lastOne = pos+step;
                if(lastOne>end){
                    lastOne = end;
                }
                CountTackDemo subTask = new CountTackDemo(pos,lastOne);
                pos+=step+1;
                subTasks.add(subTask);
            }
            invokeAll(subTasks);
            for(CountTackDemo demo: subTasks){
                sum+=demo.join();
            }
        }
        return sum;
    }

    public static void main (String [] args){
        ForkJoinPool pool = new ForkJoinPool();
        CountTackDemo demo = new CountTackDemo(1,2000000000L);
        ForkJoinTask<Long> result = pool.submit(demo);
        try{
            long res = result.get();
            System.out.println("res="+res);
        }catch(Exception e){
            e.printStackTrace();
        }
//        long count=0L ;
//        for(long i=1;i<20000;i++){
//            count+=i;
//        }
//        System.out.println("count="+count);
    }
}
