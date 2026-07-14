/**
 * 图书馆藏品抽象类
 * 属性：编号、标题、借出状态
 * 实现 Comparable 按标题排序
 */
public abstract class Item implements Comparable<Item> {
    private String id;
    private String title;
    private boolean isBorrowed;

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

    public abstract String getType();

    public boolean borrow(){
        if(isBorrowed){
            return false;
        }
        isBorrowed = true;
        return true;
    }

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
