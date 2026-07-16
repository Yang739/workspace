/**
 * 模拟银行转账测试类
 * <p>
 *     测试正常转账、非法金额、余额不足、异常包装等场景
 * </p>
 */
public class TestBankTransfer {
    public static void main(String[] args) {
        BankAccount alice = new BankAccount("A1001", 5000);
        BankAccount bob = new BankAccount("B2002", 2000);

        System.out.println("----- 正常转账 -----");
        try {
            alice.transfer(bob, 1000);
        } catch (InsufficientBalanceException e){
            System.out.println("错误：" + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("捕获到运行时异常：" + e.getMessage());
            System.out.println("原始异常：" + e.getCause().getClass().getSimpleName());
            System.out.println("原始异常消息：" + e.getCause().getMessage());
        } finally{
            System.out.println("本次操作结束\n");
        }

        System.out.println("----- 非法金额 -----");
        try {
            alice.transfer(bob, -500);
        } catch (InsufficientBalanceException e) {
            System.out.println("错误：" + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("捕获到非法金额异常：" + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("捕获到运行时异常：" + e.getMessage());
            System.out.println("原始异常：" + e.getCause().getClass().getSimpleName());
            System.out.println("原始异常消息：" + e.getCause().getMessage());
        } finally {
            System.out.println("本次操作结束\n");
        }

        System.out.println("----- 余额不足 -----");
        try{
            alice.transfer(bob, 5000);
        }catch (InsufficientBalanceException e){
            System.out.println("捕获到余额不足异常：" + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("捕获到运行时异常：" + e.getMessage());
            System.out.println("原始异常：" + e.getCause().getClass().getSimpleName());
            System.out.println("原始异常消息：" + e.getCause().getMessage());
        } finally {
            System.out.println("本次操作结束\n");
        }

        System.out.println("----- 异常链演示（可能发生，取决于随机数） -----");
        for (int i = 0; i < 10; i++) {
            try {
                alice.transfer(bob, 100);
                break;
            } catch (InsufficientBalanceException e) {
                System.out.println("错误：" + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("捕获到运行时异常：" + e.getMessage());
                System.out.println("原始异常类型：" + e.getCause().getClass().getSimpleName());
                System.out.println("原始异常消息：" + e.getCause().getMessage());
            } finally {
                System.out.println("本次操作结束\n");
            }
        }

        System.out.println("最终余额：");
        System.out.println("Alice: " + alice.getBalance());
        System.out.println("Bob: " + bob.getBalance());
    }
}
