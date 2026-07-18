/**
 * 抽奖线程任务
 */
public class DrawRunnable implements Runnable {
    private final PrizePool prizePool;
    private final String name;


    public DrawRunnable(PrizePool prizePool, String name) {
        this.prizePool = prizePool;
        this.name = name;
    }

    public PrizePool getPrizePool() {
        return prizePool;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run () {
        while (true) {
            String prize = prizePool.draw();
            if (prizePool.isEmpty()) {
                break;
            }
            System.out.printf("[%s] 抽到了：%s%n", name, prize);
            try {
                Thread.sleep(100);  //模拟抽奖间隔
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(name + " 被中断");
                break;
            }
        }
        System.out.println(name + " 结束抽奖");
    }
}
