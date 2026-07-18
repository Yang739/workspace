import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 奖品池类
 * <p>线程安全地提供随机抽取奖品</p>
 */
public class PrizePool {
    private final List<String> prizes = new ArrayList<>();
    private final Random random = new Random();

    /**
     * 初始化奖品池
     * @param items 奖品列表
     */
    public PrizePool (List<String> items) {
        prizes.addAll(items);
    }

    /**
     * 抽奖方法
     * <p>
     *     <ul>
     *         <li>每次只能一个线程进入</li>
     *         <li>从奖品列表中随机移除一个奖品并返回</li>
     *     </ul>
     * </p>
     * @return 抽到的奖品名称；若箱子为空返回 null
     */
    public synchronized String draw () {
        if (prizes.isEmpty()) {
            return null;
        }
        int index = random.nextInt(prizes.size());
        return prizes.remove(index);
    }

    public synchronized boolean isEmpty (){
        return prizes.isEmpty();
    }
}
