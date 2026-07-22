import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 阶段五：自动备份守护线程。
 * 每隔固定间隔把当前数据另存成 students_backup.dat。
 * 由 TestStudentManager 设为守护线程(setDaemon(true))，主程序退出后 JVM 自动结束它。
 */
public class BackupThread extends Thread {
    private final StudentManager manager;
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 备份间隔：题目要求是 30 秒。调试时可临时改小（如 3000）观察日志。
    private static final long INTERVAL_MS = 30_000;

    public BackupThread(StudentManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        // 用“中断标志”作为退出条件，比 while(true) 更易被外部干净地停掉
        while (!Thread.currentThread().isInterrupted()) {
            try {
                manager.backup("students_backup.dat");   // 内部读 students 时已加锁
                System.out.println("[备份线程] 数据已备份，时间: " + LocalDateTime.now().format(FMT));
                Thread.sleep(INTERVAL_MS);               // 睡在锁外，不阻塞别人
            } catch (InterruptedException e) {
                // sleep 被中断 → 恢复中断标志并退出循环（标准姿势，见 LotterySystem）
                Thread.currentThread().interrupt();
                break;
            } catch (IOException e) {
                // 单次备份失败不应让整个线程死掉，记日志后继续下一轮
                System.out.println("[备份线程] 备份失败: " + e.getMessage());
            }
        }
    }
}
