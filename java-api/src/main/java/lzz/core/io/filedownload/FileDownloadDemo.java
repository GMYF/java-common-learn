package lzz.core.io.filedownload;

import lzz.core.thread.threadfactory.NamefulThreadFactory;

import java.io.*;
import java.util.concurrent.*;

public class FileDownloadDemo {
    /**
     *     定义队列,存放下载的任务
     */
    private static BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>();
    private static ThreadFactory factory =  new NamefulThreadFactory("测试线程");
    private static RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(4,4,200,
            TimeUnit.SECONDS,queue,factory,handler);
    private static ThreadLocal<byte[]> threadLocal = new ThreadLocal<>();
    private static InputStream inputStream ;
     class DownloadJob implements Runnable{
        /**
         * 定义线程的下载位置
         */
        private int startPos;
        /**
         *  定义当前线程负责下载的文件大小
          */
        private int currentPartSize;

        /**
         * 当前线程需要下载的文件块
         */
        private RandomAccessFile currentPart;
        /**
         * 定义已经该线程已下载的字节数
         */
        public  int length ;

        public DownloadJob(int startPos,int currentPartSize,RandomAccessFile currentPart){
            this.startPos =startPos;
            this.currentPartSize =currentPartSize;
            this.currentPart = currentPart;
        }
        @Override
        public void run() {
            try {
                // 跳过startPos个字节，表明该线程只下载自己负责哪部分文件。
                inputStream.skip(this.startPos);
                byte[] buffer = new byte[1024];
                int hasRead = 0;
                while (length < currentPartSize
                        && (hasRead = inputStream.read(buffer)) > 0)
                {
                    currentPart.write(buffer, 0, hasRead);
                    // 累计该线程下载的总大小
                    length += hasRead;
                }
                currentPart.close();
//                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public InputStream getStream() throws IOException {
        File file = new File("C:\\Users\\Admin\\Downloads\\Programs","StarUML Setup 3.0.2.exe");
        FileInputStream fis = null;
        if(file.isFile()){
            fis = new FileInputStream(file);
        }
        return  fis;
    }

    public static void main(String []args){
//        FileDownloadDemo com.lzz.demo = new FileDownloadDemo();
//        try {
//            inputStream =  com.lzz.demo.getStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        for(int i=0; i<4;i++){
////            DownloadJob job = new FileDownloadDemo.DownloadJob(bytesList.get(i));
////            executor.execute(job);
////        }

    }

    // 定义下载资源的路径
    private String path;
    // 指定所下载的文件的保存位置
    private String targetFile;
    // 定义需要使用多少线程下载资源
    private int threadNum;
    // 定义下载的线程对象
    private DownloadJob[] threads;
    // 定义下载的文件的总大小
    private int fileSize;

    public FileDownloadDemo(String path, String targetFile, int threadNum)
    {
        this.path = path;
        this.threadNum = threadNum;
        threads = new DownloadJob[threadNum];
        this.targetFile = targetFile;
    }

    public void download() throws Exception{
        // 得到文件大小
        fileSize = inputStream.available();
        int currentPartSize = fileSize / threadNum + 1;
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        // 设置本地文件的大小
        file.setLength(fileSize);
        file.close();
        for (int i = 0; i < threadNum; i++)
        {
            // 计算每条线程的下载的开始位置
            int startPos = i * currentPartSize;
            // 每个线程使用一个RandomAccessFile进行下载
            RandomAccessFile currentPart = new RandomAccessFile(targetFile,
                    "rw");
            // 定位该线程的下载位置
            currentPart.seek(startPos);
//            // 创建下载线程
//            threads[i] = new DownThread(startPos, currentPartSize,
//                    currentPart);
//            // 启动下载线程
//            threads[i].start();
            DownloadJob job = new DownloadJob(startPos,currentPartSize,currentPart);
            executor.execute(job);
        }
    }
}
