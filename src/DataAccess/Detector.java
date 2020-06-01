package DataAccess;

import BusinessLogic.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.net.URL;
import java.nio.charset.*;

//探测器(爬虫)
public class Detector {
    
    //微博热搜探测器
    public static List<HotItem> weiboDetector() throws IOException, ClassNotFoundException{    
        String urlString = "https://s.weibo.com/top/summary";//微博热搜URL
        
        try{
            String webpageContent = download(new URL(urlString), "UTF-8");//获取网页内容
            List<HotItem> items = parseItems(webpageContent, 0);
            return HotItemsParser.hotItemsParser(items);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
       
    }
    
    //知乎热榜探测器
    public static List<HotItem> zhihuDetector() throws IOException, ClassNotFoundException{
        String urlString = "https://www.zhihu.com/billboard";//知乎热榜URL

        try{
            String webpageContent = download(new URL(urlString), "UTF-8");//获取网页内容
            List<HotItem> items = parseItems(webpageContent, 1); 
            return HotItemsParser.hotItemsParser(items);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        
    }
    
    //百度实时热点探测器
    public static List<HotItem> baiduDetector() throws IOException, ClassNotFoundException{
        String urlString = "http://top.baidu.com/buzz?b=1";//百度实时热点URL
        
        try{
            String webpageContent = download(new URL(urlString), "GB2312");//获取网页内容
            List<HotItem> items = parseItems(webpageContent, 3);
            return HotItemsParser.hotItemsParser(items);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

    }
    
    //下载网页内容
    public static String download(URL url, String charset)
		throws Exception
    {
	try(InputStream input = url.openStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream())
	{
            byte[] data = new byte[1024];
            int length;
            while((length=input.read(data))!=-1){
		output.write(data,0,length);
            }
            byte[] content = output.toByteArray();
            return new String(content, Charset.forName(charset));
	}catch(Exception ex){
            Warning.NoNetworkWarning();
            return null;
        }
    }
    
    //网页内容解析器
    private static List<HotItem> parseItems(String webpageContent, int id){
        
        //微博热搜解析
        if(id == 0){
            String patternString = "<td class=\"td-01 ranktop\">(.*)</td>\n" +
"                            <td class=\"td-02\">\n" +
"                                                                <a href=\"(.*?)\" target=\"_blank\">(.*?)</a>";//模式串
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(webpageContent);
        
            List<HotItem> list = new ArrayList<>();
        
            while(matcher.find()){
                list.add(new HotItem(matcher.group(1), matcher.group(3), "https://s.weibo.com" + matcher.group(2)));
            }
        
            return list;
        }

        //知乎热榜解析
        if(id == 1){
            int count = 0;
            String patternString = "\"cardId\":\"Q_(.*?)\",\"feedSpecific\":\\{(.*?)\\},\"target\":\\{\"titleArea\":\\{\"text\":\"(.*?)\"";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(webpageContent);
        
            List<HotItem> list = new ArrayList<>();

            while(matcher.find()){
                String strTitle = matcher.group(3);
                String strCardId = matcher.group(1);
                //patternString2使用title进行匹配图片的url
                //例如："<img src="..." alt="..."/>"
                String patternString2 = "<div class=\"HotList-itemTitle\">" + strTitle +"</div>(.*?)<img src=\"(.*?)\" alt=\"" + strTitle + "\"/>";
                Pattern pattern2 = Pattern.compile(patternString2, Pattern.CASE_INSENSITIVE);
                Matcher matcher2 = pattern2.matcher(webpageContent);

                String strPictureUrl = "";
                if(matcher2.find()){
                    strPictureUrl = matcher2.group(2);
                }
                list.add(new HotItem("" + (++count), strTitle, "https://zhihu.com/question/" + strCardId, strPictureUrl));
            }
        
            return list;
        }
        
        //百度实时热点解析
        else{           
            String patternString = "<span class=\"num-(.*?)\">(.*?)</span>\r\n" +
"					</td>\r\n" +
"                                    		<td class=\"keyword\">\r\n" +
"            \r\n" +
"                            <a class=\"list-title\" target=\"_blank\" href=\"(.*?)\" href_top=\"(.*?)\">(.*?)</a>";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(webpageContent);
        
            List<HotItem> list = new ArrayList<>();
        
            while(matcher.find()){
                list.add(new HotItem(matcher.group(2), matcher.group(5), matcher.group(3)));
            }
        
            return list;
        }
        
    }


}
