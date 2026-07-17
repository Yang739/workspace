import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 联系人管理器
 * <p>负责内存存储、序列化、CSV导出</p>
 */
public class ContactManager {
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public boolean removeByName(String name) {
        return contacts.removeIf(c -> c.getName().equals(name));
    }

    public Contact findByName(String name) {
        for (Contact c :contacts) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * 二进制保存，对象序列化
     *
     * @param filePath 目标文件路径
     * @throws IOException 若目标文件无法创建或写入时发生 I/O 错误
     */
    public void saveToBinaryFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(contacts);
        }
    }

    /**
     * 二进制加载，对象反序列化
     *
     * @param filePath 源文件路径
     * @throws IOException 若文件读取发生 I/O 错误，或数据无法被识别为联系人列表时抛出
     */
    @SuppressWarnings("unchecked")
    public void loadFromBinaryFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("数据文件不存在，初始化为空列表");
            contacts = new ArrayList<>();
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("无法识别的数据格式", e);
        }
    }

    /**
     * 导出为 CSV, UTF-8编码
     *
     * @param filePath 目标 CSV 文件路径
     * @throws IOException 若文件无法创建或写入时发生 I/O 错误
     */
    public void exportToCSV(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.write("姓名，电话，邮箱");
            writer.newLine();
            for (Contact c : contacts) {
                writer.write(String.format("%s，%s，%s",c.getName(),c.getPhone(),c.getEmail()));
                writer.newLine();
            }
        }
    }
}
