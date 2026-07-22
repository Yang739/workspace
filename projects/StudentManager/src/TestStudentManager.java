import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class TestStudentManager {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int LOW_SCORE_THRESHOLD = 180;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        manager.loadFromDat("students.dat");

        // 阶段五：启动自动备份守护线程（每 30 秒另存 students_backup.dat）
        BackupThread backupThread = new BackupThread(manager);
        backupThread.setDaemon(true);   // 主线程退出后，JVM 发现只剩守护线程会自动结束
        backupThread.start();

        while (true) {
            System.out.println("1.录入  2.按总分降序显示  3.删除低分(<180,需确认)  4.显示最高分  5.各科平均分(三线程)  6.导出学生成绩单  0.退出");
            String choice = scanner.nextLine().trim();
            if (choice.equals("0")) break;

            if (choice.equals("2")) {
                for (Student s : manager.getSortedByTotal()) {
                    System.out.println(s);
                }
                continue;
            }

            if (choice.equals("3")) {
                List<Student> low = manager.findBelowTotal(LOW_SCORE_THRESHOLD);
                if (low.isEmpty()) {
                    System.out.println("没有总分低于 " + LOW_SCORE_THRESHOLD + " 的学生");
                    continue;
                }
                System.out.println("以下 " + low.size() + " 名学生总分低于 " + LOW_SCORE_THRESHOLD + "：");
                low.forEach(System.out::println);
                System.out.print("确认删除？(y/n)：");
                String confirm = scanner.nextLine().trim();
                if (confirm.equalsIgnoreCase("y")) {
                    List<Student> removed = manager.removeBelowTotal(LOW_SCORE_THRESHOLD);
                    manager.saveToDat("students.dat");   // 删除也要落盘（之前漏了这步）
                    System.out.println("已删除 " + removed.size() + " 名学生");
                } else {
                    System.out.println("已取消，未删除");
                }
                continue;
            }

            if (choice.equals("4")) {
                Student top = manager.findTop();
                if (top == null) System.out.println("暂无学生");
                else System.out.println("总分最高的学生：" + top);
                continue;
            }

            if (choice.equals("5")) {
                int n = manager.size();
                if (n == 0) {
                    System.out.println("暂无学生");
                } else {
                    // 阶段五：开三个线程分别计算语文/数学/英语总分，
                    // 主线程用 CountDownLatch 等它们全算完，再汇总打印。
                    final double[] sums = new double[3];        // 三线程各写自己的下标，互不冲突
                    final String[] names = {"语文", "数学", "英语"};
                    CountDownLatch latch = new CountDownLatch(3);
                    for (int i = 0; i < 3; i++) {
                        final int subj = i;
                        new Thread(() -> {
                            try {
                                sums[subj] = manager.sumOfSubject(subj);  // 锁内遍历，读到一致快照
                            } finally {
                                latch.countDown();   // 无论成败都减一，主线程不会死等
                            }
                        }).start();
                    }
                    try {
                        latch.await();   // 主线程阻塞，直到 3 个线程都 countDown
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    for (int i = 0; i < 3; i++) {
                        System.out.printf("%s 平均分：%.2f%n", names[i], sums[i] / n);
                    }
                }
                continue;
            }

            if (choice.equals("6")) {
                manager.exportToTxt("students.txt");
                System.out.println("已导出 students.txt");
                continue;
            }

            if (!choice.equals("1")) continue;

            System.out.print("学号：");
            String id = scanner.nextLine().trim();
            if (id.length() != 8) {
                System.out.println("请输入 8 位学号");
                continue;
            }
            System.out.print("姓名：");
            String name = scanner.nextLine().trim();

            int[] scores = new int[3];   // 每个学生独立的数组
            for (int i = 0; i < 3; ) {
                try {
                    System.out.print("第" + (i + 1) + "门成绩(0-100)：");
                    int x = Integer.parseInt(scanner.nextLine().trim());
                    if (x < 0 || x > 100) {
                        throw new InvalidScoreException("成绩必须在 0-100");
                    }
                    scores[i] = x;
                    i++;
                } catch (NumberFormatException e) {
                    System.out.println("请输入数字");
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }

            String timeStr = LocalDateTime.now().format(dtf);
            // 让 Student 自己根据成绩数组算总分，调用方不再算 sum（避免把上个学生分数累加进来）
            Student s = new Undergraduate(id, name, scores[0], scores[1], scores[2], timeStr);
            if (manager.addStudent(s)) {
                System.out.println("录入成功：" + s);
            } else {
                System.out.println("学号 " + id + " 已存在，未重复录入");
            }
            manager.saveToDat("students.dat");
        }
        scanner.close();
    }
}
