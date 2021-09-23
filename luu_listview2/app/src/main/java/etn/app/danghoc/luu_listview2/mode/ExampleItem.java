package etn.app.danghoc.luu_listview2.mode;

public class ExampleItem {
    private String title;
    private String content;

    public void setTitle(String title){
        this.title=title;
    }
    public void setContent(String content){
        this.content=content;
    }

    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }

    public ExampleItem(String title,String content){
        this.title=title;
        this.content=content;
    }
}
