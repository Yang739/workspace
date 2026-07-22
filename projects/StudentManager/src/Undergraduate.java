public class Undergraduate extends Student {
    public Undergraduate(String id, String name, int chinese, int math, int english, String time) {
        super(id, name, chinese, math, english, time);
    }
    // 总分 = 三门之和，父类 computeTotal() 已经算对，无需重写
}
