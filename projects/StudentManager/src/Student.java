import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

public abstract class Student implements Comparable<Student>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private int[] scores = new int[3];   // 下标 0=语文 1=数学 2=英语
    private int total;                     // 总分
    private String time;                   // 录入时间

    public Student(String id, String name, int chinese, int math, int english, String time) {
        this.id = id;
        this.name = name;
        this.scores[0] = chinese;
        this.scores[1] = math;
        this.scores[2] = english;
        this.time = time;
        computeTotal();                    // 构造时自动算一次总分
    }

    // 模板方法：本科生总分 = 三门之和；研究生重写此方法加上论文成绩
    public void computeTotal() {
        setTotal(scores[0] + scores[1] + scores[2]);
    }

    // 提供给子类重写 computeTotal 时写回总分用（private 字段不允许子类直接赋值）
    protected void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    // 设置某一科成绩后，自动重算总分（满足“设置总分时自动根据成绩数组求和”）
    public void setScore(int index, int value) {
        if (index < 0 || index > 2) throw new IllegalArgumentException("成绩下标必须是 0~2");
        scores[index] = value;
        computeTotal();
    }

    public int getScore(int index) {
        return scores[index];
    }

    // 返回数组副本，避免外部直接改到内部数组（封装性）
    public int[] getScores() {
        return Arrays.copyOf(scores, scores.length);
    }

    public String getId() {
        return id;
    }

    // 原代码这里写成 void setId()，没有参数，等于把字段赋给自己（空操作）。
    // 正确写法要接收参数。
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%s-%s-%d-%d-%d-%d-%s",
                id, name, scores[0], scores[1], scores[2], total, time);
    }

    // 正确的 Comparable 实现：总分降序，同分按学号升序
    @Override
    public int compareTo(Student o) {
        int cmp = Integer.compare(o.total, this.total);  // o 在前 → 降序
        if (cmp != 0) return cmp;
        return this.id.compareTo(o.id);                  // 学号升序
    }
}
