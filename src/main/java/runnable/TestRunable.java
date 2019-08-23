package runnable;

public class TestRunable implements Runnable{
    @Override
    public void run() {
        for(int i =0 ;i<10;i++){
            System.out.println(Thread.currentThread().getName()+"开始运行"+i+"次");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
