/**
 * 图书馆借阅系统测试类
 * <p>演示添加藏品、显示、排序、借还操作以及罚金计算</p>
 */
public class TestLibrary {
    public static void main(String[] args) {
        Library lib = new Library();

        // 添加测试数据
        lib.addItem(new Book("B001", "Java核心技术", "霍斯特曼"));
        lib.addItem(new Book("B002", "深入理解Java虚拟机", "周志明"));
        lib.addItem(new DVD("D001", "黑客帝国", 136));
        lib.addItem(new DVD("D002", "泰坦尼克号", 195));

        // 显示所有
        lib.displayAll();

        // 按标题排序显示
        lib.displaySorted();

        // 借阅测试
        System.out.println("\n===== 借阅操作 =====");
        lib.borrowItem("B001");
        lib.borrowItem("B001");
        lib.borrowItem("B003");

        // 归还测试
        System.out.println("\n===== 归还操作 =====");
        lib.returnItem("B001");
        lib.returnItem("B001");
        lib.returnItem("B003");

        // 罚金计算演示
        System.out.println("\n===== 逾期罚款计算 =====");
        Item b1 = new Book("X", "任意", "作者");
        Item d1 = new DVD("Y", "任意", 120);
        System.out.println("图书逾期10天罚款：" + ((Chargeable) b1).getFine(10) + "元");
        System.out.println("DVD逾期5天罚款：" + ((Chargeable) d1).getFine(5) + "元");
    }
}
