/**
 * 图书馆类，管理藏品列表
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Library {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item){
        items.add(item);
    }

    private Item findById(String id){
        for (Item item : items){
            if (item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    public boolean borrowItem(String id){
        Item item = findById(id);
        if(item == null){
            System.out.println("错误：未找到编号为 " + id + " 的藏品");
            return false;
        }
        boolean success = item.borrow();
        if (success){
            System.out.println("成功借出：" + item.getTitle());
        }else {
            System.out.println("借出失败：该" + item.getType() + "已被借出");
        }
        return success;
    }

    public boolean returnItem(String id){
        Item item = findById(id);
        if(item == null){
            System.out.println("错误：未找到编号为 " + id + " 的藏品");
            return false;
        }
        boolean success = item.returnItem();
        if (success){
            System.out.println("成功归还：" + item.getTitle());
        }else {
            System.out.println("归还失败：该" + item.getType() + "尚未借出");
        }
        return success;
    }

    public void displayAll(){
        if (items.isEmpty()){
            System.out.println("图书馆暂无藏品");
            return;
        }
        System.out.println("========== 所有藏品 ==========");
        for (Item item : items){
            System.out.println(item);
        }
    }

    //按 Unicode 编码排序
    public void displaySorted(){
        List<Item> sortedList = new ArrayList<>(items);
        Collections.sort(sortedList);
        System.out.println("========== 按标题排序 ==========");
        for (Item item : sortedList){
            System.out.println(item);
        }
    }
}