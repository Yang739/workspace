import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 多线程抽奖箱测试类
 * <p>
 *     <ul>
 *         <li>两个 Runnable 线程同步抽奖</li>
 *         <li>Callable + FutureTask 抽奖</li>
 *         <li>线程池 + CountDownLatch 批量抽奖</li>
 *     </ul>
 * </p>
 */
public class TestLottery {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<String> items = Arrays.asList("手机", "电脑", "耳机", "键盘", "鼠标",
                "音箱", "显示器", "平板", "手表", "充电宝");

        System.out.println("========== 方式一：两个Runnable线程同步抽奖 ==========");
        PrizePool pool1 = new PrizePool(items);
        Thread threadA = new Thread(new DrawRunnable(pool1, "线程A"));
        Thread threadB = new Thread(new DrawRunnable(pool1, "线程B"));
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        System.out.println();

        System.out.println("========== 方式二：Callable + FutureTask 抽奖 ==========");
        PrizePool pool2 = new PrizePool(items);
        Callable<String> callable = new DrawCallable(pool2);

        // 用FutureTask封装，用线程执行
        FutureTask<String> futureTask1 = new FutureTask<>(callable);
        FutureTask<String> futureTask2 = new FutureTask<>(callable);
        new Thread(futureTask1,"Future-1").start();
        new Thread(futureTask2,"Future-2").start();

        // 获取结果
        String result1 = futureTask1.get();
        String result2 = futureTask2.get();
        System.out.println("Future1 结果: " + result1);
        System.out.println("Future2 结果: " + result2);
        System.out.println();

        System.out.println("========== 方式三：线程池 + CountDownLatch ==========");
        PrizePool pool3 = new PrizePool(items);
        int taskCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(taskCount);

        for (int i = 0; i < taskCount; i++) {
            executor.execute(() -> {
                try {
                    String prize = pool3.draw();
                    if (prize != null) {
                        System.out.printf("[线程池-%s] 抽到了：%s%n",
                                Thread.currentThread().getName(), prize);
                    } else {
                        System.out.println("奖品已抽完");
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println("抽奖结束！");
    }
}
