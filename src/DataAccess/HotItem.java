package DataAccess;

//用于表示热门条目
public class HotItem {
    private int rank;//排名
    private String content;//内容
    private String urlString;//网址
    private String urlImgString;//图片的网址
    
    public HotItem(String rankString, String content, String urlString){
        this.rank = Integer.parseInt(rankString);
        this.content = content;
        this.urlString = urlString;
        this.urlImgString = "";
    }
    
    public HotItem(String rankString, String content, String urlString, String urlImgString){
        this(rankString, content, urlString);
        this.urlImgString = urlImgString;
    }

    public int getRank(){
        return rank;
    }
    
    public String getContent(){
        return content;
    }
    
    public String getUrlString(){
        return urlString;
    }

    public String getUrlImgString(){
        return urlImgString;
    }
    
    @Override
    public String toString(){
        return rank + " " + content + " " + urlString;
    }
}