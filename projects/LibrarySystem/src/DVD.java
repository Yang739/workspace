/**
 * DVD 类
 * <p>
 *     <ul>
 *         <li>继承 {@link Item} 并实现 {@link Chargeable} 接口</li>
 *         <li>增加时长属性，罚款每天1元</li>
 *     </ul>
 * </p>
 */
public class DVD extends Item implements Chargeable {

    /** 影片时长（分钟） */
    private final int duration;

    /**
     * 构造一张 DVD
     * @param id 编号
     * @param title 标题
     * @param duration 时长
     */
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
