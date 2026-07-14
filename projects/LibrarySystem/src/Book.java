/**
 * 图书类，继承 Item 并实现 Chargeable 接口
 * 增加作者属性，罚款每天0.5元
 */
public class Book extends Item implements Chargeable{
    private String author;

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
