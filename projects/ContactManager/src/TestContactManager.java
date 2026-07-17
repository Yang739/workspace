import java.io.*;

/**
 * 联系人管理应用主程序
 * <p>演示序列化、CSV导出、文件复制性能对比</p>
 */
public class TestContactManager {

    public static void main(String[] args) {
        ContactManager manager = new ContactManager();

        try {
            manager.loadFromBinaryFile("contacts.obj");
        } catch (IOException e) {
            System.out.println("加载数据失败: " + e.getMessage());
        }

        if (manager.getContacts().isEmpty()) {
            manager.addContact(new Contact("张三", "13800138000", "zhangsan@example.com"));
            manager.addContact(new Contact("李四", "13900139000", "lisi@example.com"));
            manager.addContact(new Contact("王五", "13700137000", "wangwu@example.com"));
            System.out.println("已添加初始联系人");
        }

        System.out.println("========== 当前联系人列表 ==========");
        for (Contact c : manager.getContacts()) {
            System.out.println(c);
        }

        Contact found = manager.findByName("李四");
        System.out.println("\n查找李四: " + (found == null ? "未找到" : found));

        boolean remove = manager.removeByName("王五");
        System.out.println("\n删除王五: " + (remove ? "成功" : "失败"));

        try {
            manager.saveToBinaryFile("contacts.obj");
            System.out.println("\n联系人已保存到 contacts.obj");
        } catch (IOException e) {
            System.out.println("保存失败: " + e.getMessage());
        }

        try {
            manager.exportToCSV("contacts.csv");
            System.out.println("联系人已导出到 contacts.csv");
        } catch (IOException e) {
            System.out.println("导出失败: " + e.getMessage());
        }

        // 文件复制工具测试
        System.out.println("\n========== 文件复制性能对比 ==========");
        String srcFile = "contacts.obj";
        String destSlow = "contacts_copy_slow.tmp";
        String destFast = "contacts_copy_fast.tmp";
        copyFileCompare(srcFile, destSlow, destFast);
    }

    public static void copyFileCompare(String srcPath, String slowDest, String fastDest) {
        File src = new File(srcPath);
        if (!src.exists()) {
            System.out.println("源文件不存在，跳过复制测试。");
            return;
        }

        // 普通字节流复制
        long start = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(src);
             FileOutputStream fos = new FileOutputStream(slowDest)) {
            int b;
            while ((b = fis.read()) != -1) {
                fos.write(b);
            }
        } catch (IOException e) {
            System.out.println("普通流复制失败: " + e.getMessage());
            return;
        }

        long slowTime = System.currentTimeMillis() - start;
        System.out.println("普通字节流复制耗时: " + slowTime + " ms");

        // 缓冲字节流复制
        start = System.currentTimeMillis();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fastDest))) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while((bytesRead = bis.read()) != -1) {
                bos.write(buffer, 0 ,bytesRead);
            }
        } catch (IOException e) {
            System.out.println("缓冲流复制失败: " + e.getMessage());
            return;
        }

        long fastTime = System.currentTimeMillis() - start;
        System.out.println("缓冲字节流复制耗时: " + fastTime + " ms");

        if (slowTime > 0) {
            System.out.printf("性能提升约 %.1f 倍%n", (double) slowTime / fastTime);
        }
    }
}
