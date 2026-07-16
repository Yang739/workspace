/**
 * 图书馆藏品抽象类
 * <p>
 *     <ul>
 *         <li>包含共有属性：编号、标题、借出状态</li>
 *         <li>实现 {@link Comparable} 接口，支持按标题进行自然排序</li>
 *     </ul>
 * </p>
 */
public abstract class Item implements Comparable<Item> {

    /**藏品唯一编号*/
    private final String id;

    /**藏品标题*/
    private final String title;

    /**藏品状态，false表示在馆，true表示借出*/
    private boolean isBorrowed;

    /**
     * 构造一个新的藏品
     *
     * @param id 编号（不应为 null 或空，此处不校验）
     * @param title 标题（不应为 null 或空）
     */
    public Item (String id,String title){
        this.id = id;
        this.title = title;
        this.isBorrowed = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed(){
        return isBorrowed;
    }

    /**
     * 获取藏品类型，由子类实现
     *
     * @return 类型名称，如 "图书" 或 "DVD"
     */
    public abstract String getType();

    /**
     * 借出藏品
     * <p>
     *     <ul>
     *         <li>如果当前藏品       未被借出，则标记为已借出并返回 true</li>
     *         <li>否则返回 false</li>
     *     </ul>
     * </p>
     *
     * @return 借出是否成功
     */
    public boolean borrow(){
        if(isBorrowed){
            return false;
        }
        isBorrowed = true;
        return true;
    }

    /**
     * 归还藏品
     * <p>
     *     <ul>
     *         <li>如果当前处于已借出状态，则标记为在馆并返回 true</li>
     *         <li>否则返回 false</li>
     *     </ul>
     * </p>
     *
     * @return 归还是否成功
     */
    public boolean returnItem(){
        if(!isBorrowed){
            return false;
        }
        isBorrowed = false;
        return true;
    }

    @Override
    public int compareTo(Item other){
        return this.title.compareTo(other.title);
    }

    @Override
    public String toString(){
        return String.format("[%s] %s - %s (%s)",
                getType(), id, title, isBorrowed ? "已借出" : "在馆");
    }
}
