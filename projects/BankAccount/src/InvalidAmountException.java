/**
 * 非法金额异常（非受检异常）
 * <p>转账金额为负数或零时抛出</p>
 */
public class InvalidAmountException extends RuntimeException {

    /**
     * 构造一个非法金额异常
     *
     * @param message 异常描述信息
     */
    public InvalidAmountException (String message){
        super(message);
    }
}
