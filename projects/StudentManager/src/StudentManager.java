import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private final Map<String, Student> index = new HashMap<>();   // 学号 → 学生，O(1) 查找

    // 阶段五：用一把“专用锁”保护 students + index 这整份共享状态。
    // 主线程(增删改/显示)、备份守护线程(读)、计算线程(读) 都过这把锁，
    // 保证“读到的永远是一致快照”，杜绝 ConcurrentModificationException 和数据错乱。
    private final Object lock = new Object();

    // 增：学号重复则拒绝，返回是否成功
    public boolean addStudent(Student s) {
        synchronized (lock) {
            if (index.containsKey(s.getId())) return false;
            students.add(s);
            index.put(s.getId(), s);
            return true;
        }
    }

    // 删（按学号）
    public boolean removeById(String id) {
        synchronized (lock) {
            Student s = index.remove(id);
            if (s == null) return false;
            students.remove(s);
            return true;
        }
    }

    // 查（O(1)）
    public Student findById(String id) {
        synchronized (lock) {
            return index.get(id);
        }
    }

    // 排序（原地，依赖 Comparable：总分降序、同分学号升序）
    public void sortByTotal() {
        synchronized (lock) {
            Collections.sort(students);
        }
    }

    // 返回按总分降序排序的副本（不破坏原集合顺序）
    public List<Student> getSortedByTotal() {
        List<Student> copy;
        synchronized (lock) {
            copy = new ArrayList<>(students);
        }
        Collections.sort(copy);
        return copy;
    }

    // 预览：总分低于阈值的学生（供“确认提示”使用）
    public List<Student> findBelowTotal(int threshold) {
        synchronized (lock) {
            List<Student> result = new ArrayList<>();
            for (Student s : students) {
                if (s.getTotal() < threshold) result.add(s);
            }
            return result;
        }
    }

    // 用 Iterator 安全删除总分低于阈值的学生，并同步维护 Map
    public List<Student> removeBelowTotal(int threshold) {
        synchronized (lock) {
            List<Student> removed = new ArrayList<>();
            Iterator<Student> it = students.iterator();
            while (it.hasNext()) {
                Student s = it.next();
                if (s.getTotal() < threshold) {
                    it.remove();                  // 通过迭代器自己删，不会 ConcurrentModificationException
                    index.remove(s.getId());       // 同步从 Map 移除，保持两份数据一致
                    removed.add(s);
                }
            }
            return removed;
        }
    }

    // 统计每门课平均分（单线程版本，留作三线程结果的对照基准）
    public Map<String, Double> averageBySubject() {
        synchronized (lock) {
            Map<String, Double> result = new LinkedHashMap<>();
            if (students.isEmpty()) return result;
            double[] sum = new double[3];
            for (Student s : students) {
                sum[0] += s.getScore(0);
                sum[1] += s.getScore(1);
                sum[2] += s.getScore(2);
            }
            int n = students.size();
            result.put("语文", sum[0] / n);
            result.put("数学", sum[1] / n);
            result.put("英语", sum[2] / n);
            return result;
        }
    }

    // 单科总分（供“三线程并发计算”使用；在锁内遍历，保证读到一致的快照）
    public int sumOfSubject(int subject) {
        synchronized (lock) {
            int sum = 0;
            for (Student s : students) sum += s.getScore(subject);
            return sum;
        }
    }

    // 找总分最高的学生：必须用比较器！
    // 因为本类的 Comparable 是“降序”，自然排序被反转，直接用 Collections.max(students)
    // 会返回总分最低的。用 comparingInt(Student::getTotal) 按总分升序比，才拿得到真正最高分。
    public Student findTop() {
        synchronized (lock) {
            if (students.isEmpty()) return null;
            return Collections.max(students, Comparator.comparingInt(Student::getTotal));
        }
    }

    // 持久化保存（阶段四）；读 students 在锁内，备份线程不会读到半成品
    public void saveToDat(String filePath) throws IOException {
        synchronized (lock) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(students);
            }
        }
    }

    // 备份（另存一份到指定路径，阶段五守护线程调用）
    public void backup(String filePath) throws IOException {
        saveToDat(filePath);   // saveToDat 本身已加锁，可重入
    }

    @SuppressWarnings("unchecked")
    public void loadFromDat(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            synchronized (lock) { students = new ArrayList<>(); }
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Student> loaded = (List<Student>) ois.readObject();
            synchronized (lock) {
                students = loaded;
                rebuildIndex();   // rebuildIndex 内部也会取同一把锁（可重入）
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("数据格式无法识别", e);
        }
    }

    public void rebuildIndex() {
        synchronized (lock) {
            index.clear();
            for (Student s : students) {
                index.put(s.getId(), s);
            }
        }
    }

    public void exportToTxt(String filePath) throws IOException {
        // 先取锁内副本，再在锁外写盘（写盘较慢，不持锁）
        List<Student> snapshot;
        synchronized (lock) { snapshot = new ArrayList<>(students); }
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            for (Student student : snapshot) {
                bw.write(student.toString());
                bw.newLine();
            }
        }
    }

    public int size() {
        synchronized (lock) { return students.size(); }
    }

    // 返回防御性副本，避免外部直接改到内部集合（封装性）
    public List<Student> getAll() {
        synchronized (lock) { return new ArrayList<>(students); }
    }
}
