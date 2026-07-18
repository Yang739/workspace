import java.util.concurrent.Callable;

/**
 * 抽奖任务
 * <p>用于返回单次抽奖结果</p>
 */
public class DrawCallable implements Callable<String> {
    private final PrizePool prizePool;

    public DrawCallable(PrizePool prizePool) {
        this.prizePool = prizePool;
    }

    public PrizePool getPrizePool() {
        return prizePool;
    }

    @Override
    public String call() {
        String prize = prizePool.draw();
        if (prize != null) {
            System.out.printf("[Callable-%s] 抽到了：%s%n",
                    Thread.currentThread().getName(), prize);
        }
        return prize;
    }
}
