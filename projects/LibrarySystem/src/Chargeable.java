/**
 * 逾期罚款接口
 * <p>
 *     定义计算逾期罚金的通用契约，由可产生罚款的藏品实现
 * </p>
 */
public interface Chargeable {
    /**
     * 计算逾期罚金
     * @param days 逾期天数
     * @return 罚金金额
     */
    double getFine(int days);
}
