/**
 * 图书馆借阅系统测试类
 */
public class TestLibrary {
    static void main(String[] args) {
        Library lib = new Library();

        lib.addItem(new Book("B001", "Java核心技术", "霍斯特曼"));
        lib.addItem(new Book("B002", "深入理解Java虚拟机", "周志明"));
        lib.addItem(new DVD("D001", "黑客帝国", 136));
        lib.addItem(new DVD("D002", "泰坦尼克号", 195));

        lib.displayAll();

        lib.displaySorted();

        System.out.println("\n===== 借阅操作 =====");
        lib.borrowItem("B001");
        lib.borrowItem("B001");
        lib.borrowItem("B003");

        System.out.println("\n===== 归还操作 =====");
        lib.returnItem("B001");
        lib.returnItem("B001");
        lib.returnItem("B003");

        System.out.println("\n===== 逾期罚款计算 =====");
        Item b1 = new Book("X", "任意", "作者");
        Item d1 = new DVD("Y", "任意", 120);
        System.out.println("图书逾期10天罚款：" + ((Chargeable) b1).getFine(10) + "元");
        System.out.println("DVD逾期5天罚款：" + ((Chargeable) d1).getFine(5) + "元");
    }
}
