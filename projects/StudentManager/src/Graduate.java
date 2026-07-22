public class Graduate extends Student {
    private final int thesis;   // 论文成绩

    public Graduate(String id, String name, int chinese, int math, int english, String time, int thesis) {
        super(id, name, chinese, math, english, time);
        this.thesis = thesis;
        computeTotal();   // 父类构造里已经算过一次（那时 thesis 还是 0），这里用正确值再算一次
    }

    // 研究生总分 = 三门成绩之和 + 论文成绩
    @Override
    public void computeTotal() {
        setTotal(getScore(0) + getScore(1) + getScore(2) + thesis);
    }

    @Override
    public String toString() {
        return String.format("%s-%s-%d-%d-%d-%d(含论文%d)-%s",
                getId(), getName(), getScore(0), getScore(1), getScore(2), getTotal(), thesis, getTime());
    }
}
