/**
 * DVD 类，继承 Item 并实现 Chargeable 接口
 * 增加时长属性，罚款每天1元
 */
public class DVD extends Item implements Chargeable {
    private int duration;

    public DVD (String id, String title, int duration){
        super(id, title);
        this.duration = duration;
    }

    public int getDuration(){
        return duration;
    }

    @Override
    public String getType(){
        return "DVD";
    }

    @Override
    public double getFine(int days){
        return days * 1.0;
    }

    @Override
    public String toString(){
        return String.format("%s 时长：%d分钟", super.toString(), duration);
    }
}
