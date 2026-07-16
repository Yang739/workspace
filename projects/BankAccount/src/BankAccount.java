/**
 * 银行账户类
 * <p>包含转账方法，自定义异常处理及异常链演示</p>
 */
public class BankAccount {

    /**账号*/
    private final String accountNumber;

    /**余额*/
    private double balance;

    /**
     * 构造一个账户
     *
     * @param accountNumber 账号
     * @param balance 余额
     */
    public BankAccount (String accountNumber, double balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * 转账操作
     *
     * @param target 目标账户
     * @param amount 转账金额
     * @throws InsufficientBalanceException 余额不足
     * @throws InvalidAmountException 金额非法
     */
    public void transfer (BankAccount target, double amount) throws InsufficientBalanceException {

        // 金额合法性检查
        if (amount <= 0){
            throw new InvalidAmountException("转账金额必须为正数，当前金额：" + amount);
        }

        // 模拟检查账户状态（可能抛出 IOException）
        try{
            checkAccountStatus();
        }catch (java.io.IOException e){
            // 包装为非受检异常，保留原始异常原因
            throw new RuntimeException("转账前账户状态检查失败，请重试", e);
        }

        // 余额检查
        if (balance < amount){
            throw new InsufficientBalanceException( "余额不足，当前余额：" + balance + "，需要：" + amount, balance);
        }

        // 执行转账
        this.balance -= amount;
        target.balance += amount;

        System.out.printf("转账成功：%s -> %s，金额 %.2f%n", accountNumber, target.accountNumber, amount);
    }

    /**
     * 模拟检查账户状态
     * @throws java.io.IOException 当模拟故障触发时抛出，包含具体错误描述
     */
    private void checkAccountStatus() throws java.io.IOException {

        // 模拟网络或系统故障
        double random = Math.random();
        if (random < 0.3){  // 30% 概率模拟故障
            throw new java.io.IOException("连接银行服务器失败：网络超时");
        }
    }
}
