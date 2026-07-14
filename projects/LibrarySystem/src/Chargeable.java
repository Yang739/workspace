/**
 * 逾期罚款接口
 */
public interface Chargeable {
    /**
     * 计算逾期罚金
     * @param days 逾期天数
     * @return 罚金金额
     */
    double getFine(int days);
}
