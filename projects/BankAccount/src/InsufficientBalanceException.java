/**
 * 余额不足异常（受检异常）
 * <p>携带当前余额信息</p>
 */
public class InsufficientBalanceException extends Exception {
    /**当前余额*/
    private double currentBalance;

    /**
     *构造一个余额不足异常
     *
     * @param message 异常描述信息
     * @param currentBalance 当前账户余额
     */
    public InsufficientBalanceException(String message, double currentBalance){
        super(message);
        this.currentBalance = currentBalance;
    }

    public double getCurrentBalance(){
        return currentBalance;
    }
}
