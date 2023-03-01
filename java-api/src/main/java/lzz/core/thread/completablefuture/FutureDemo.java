package lzz.core.thread.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FutureDemo {
    public static void testRunAsync() throws ExecutionException, InterruptedException {
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            System.out.println("测试Run");
        });
        cf.get();
//        Thread.sleep(2000);
        cf.complete("手动结束");
        System.out.println("手动结束");

    }

    public static void testSupplyAsync() throws ExecutionException, InterruptedException {
        // Supplier 无入参，有返回值
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "测试SupplyAsync";
        });
        System.out.println("等待返回结果");
        // get方法还是会阻塞主线程
        System.out.println(cf.get());
        System.out.println("返回结果为");
    }

    public static void testSupplyAsyncAndNoBlocking() throws ExecutionException, InterruptedException {
        // Supplier 无入参，有返回值
        // 执行如下计算
        // step1 (1 + 2)
        // step2 step1 * 2
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
                    return 1 + 2;
                })
                //thenApply 回调方法-返回结果
                .thenApply(preResult -> {
                    System.out.println("SupplyAsync回调方法执行");
                    return preResult * 2;
                })
                .thenApplyAsync(result -> {
                    return result % 2;
                });
                cf
                .thenApply(params -> {
                    // thenAccept 回调方法-不返回结果
                    System.out.println("上述计算结果为：" + params);
                    return params;
                })
                // 新起线程执行后续操作
                .thenAcceptAsync(params -> {
                    // thenAccept 回调方法-不返回结果
                    // 新起异步线程
                    System.out.println("休眠后上述计算结果为：" + params);

                }).thenRun(() -> {
                            // thenRun 无入参-无返回值  ，可以记录耗时等
                            System.out.println("SupplyAsync方法结束");
                        });

        System.out.println("等待返回结果");
        // get方法还是会阻塞主线程
        System.out.println(cf.get());
        System.out.println("返回结果为");
    }

    public static void main(String[] args) {
        try {
//            testRunAsync();
//            testSupplyAsync();
            testSupplyAsyncAndNoBlocking();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
