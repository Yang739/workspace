/**
 * 图书类
 * <p>
 *     <ul>
 *         <li>继承 {@link Item} 并实现 {@link Chargeable} 接口</li>
 *         <li>增加作者属性，罚款每天0.5元</li>
 *     </ul>
 * </p>
 */
public class Book extends Item implements Chargeable{

    /** 作者姓名 */
    private final String author;

    /**
     * 构造一本图书
     *
     * @param id 编号
     * @param title 标题
     * @param author 作者
     */
    public Book(String id, String title, String author){
        super(id, title);
        this.author = author;
    }

    public String getAuthor(){
        return author;
    }

    @Override
    public String getType(){
        return "图书";
    }

    @Override
    public double getFine(int days){
        return days * 0.5;
    }

    @Override
    public String toString(){
        return String.format("%s 作者 %s", super.toString(), author);
    }
}
