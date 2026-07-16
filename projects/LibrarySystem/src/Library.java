import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 图书馆类
 * <p>
 *     <ul>
 *         <li>维护一个藏品列表</li>
 *         <li>提供增删改查、借还、显示和排序功能</li>
 *     </ul>
 * </p>
 */
public class Library {

    /** 存储所有藏品的列表（当前未做唯一性校验） */
    private final List<Item> items = new ArrayList<>();

    /**
     * 添加一件藏品
     *
     * @param item 要添加的藏品（不能为 null）
     */
    public void addItem(Item item){
        items.add(item);
    }

    /**
     * 根据编号查找藏品
     * @param id 编号
     * @return 匹配的藏品对象，若不存在则返回 null
     */
    private Item findById(String id){
        for (Item item : items){
            if (item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    /**
     * 借出指定编号的藏品
     * <p>
     *     <ul>
     *         <li>如果编号不存在，输出错误信息</li>
     *         <li>如果已借出，输出提示</li>
     *         <li>借出成功则输出成功信息</li>
     *     </ul>
     * </p>
     *
     * @param id 要借出的藏品编号
     * @return 借出是否成功
     */
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

    /**
     * 归还指定编号的藏品
     * <p>
     *     <ul>
     *         <li>如果编号不存在，输出错误信息</li>
     *         <li>如果尚未借出，输出提示</li>
     *         <li>归还成功则输出成功信息</li>
     *     </ul>
     * </p>
     * @param id 编号
     * @return 归还是否成功
     */
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

    /**
     * 按添加顺序显示所有藏品的信息
     */
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

    /**
     * 按标题升序（Unicode 编码排序）显示所有藏品
     * <p>利用 Collections.sort() 对副本排序，不影响原始顺序</p>
     */
    public void displaySorted(){
        List<Item> sortedList = new ArrayList<>(items);
        Collections.sort(sortedList);
        System.out.println("========== 按标题排序 ==========");
        for (Item item : sortedList){
            System.out.println(item);
        }
    }
}