package BusinessLogic;

import DataAccess.*;
import UI.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//用于升级词汇包
public class WordBagUpdater {
    
    //检查更新
    public static boolean checkUpdate(){
        //读出云端词库的版本号
        String[] fileNames = {"techWordBag", "entertainmentWordBag", "sportWordBag"};
        try {
            String str = Detector.download(new URL("https://jproject-1302165008.cos-website.ap-beijing.myqcloud.com/"), "UTF-8");
            
            java.util.List<String> versionUrl=parse(str);
            String version = versionUrl.get(0);
            String[] urls = new String[3];
            for(int i = 0; i < 3; i++){
                urls[i] = versionUrl.get(i + 1);
            }
            //如果不需要更新
            if(version.equals(Config.getVersion())) {
                return false;
            }
            //如果需要更新，打开更新窗口
            else {
                new WordBagUpdateDialog(Config.getVersion(), version).setVisible(true);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    //更新
    public static void update(){
        String[] fileNames = {"techWordBag", "entertainmentWordBag", "sportWordBag"};
        String[] urls = new String[3];
        String version;
        try{
            String str = Detector.download(new URL("https://jproject-1302165008.cos-website.ap-beijing.myqcloud.com/"), "UTF-8");
            java.util.List<String> versionUrl=parse(str);
            version = versionUrl.get(0);
            for(int i = 0; i < 3; i++){
                urls[i] = versionUrl.get(i + 1);
            }
            //下载云端的3个文件的到本地
            for(int i = 0; i < 3; i++){
                saveUrlAs(urls[i], "./wordBags", fileNames[i]);
            }
            Config.setVersion(version);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private static java.util.List<String> parse(String text) { 
        text = text.replaceAll("\r|\n", "");
	String patternString ="Current verion:(.+)Downloadtech:(.+)Downloaden:(.+)Downloadsport:(.+)";
	Pattern pattern = Pattern.compile(patternString, 
		Pattern.CASE_INSENSITIVE  );
	Matcher matcher = pattern.matcher( text );
	java.util.List<String> list = new ArrayList<>();
	if(matcher.find()) {
            String version = matcher.group(1);
            String[] urls = {matcher.group(2),matcher.group(3),matcher.group(4)};
            list.add(version);
            for(String url : urls) {
		list.add(url);
            }
        }
        else {
            System.out.println("获取网站内容失败"); 
	}
	return list;
    }
    
    private static void saveUrlAs(String url,String filePath, String fileName){  
	FileOutputStream fileOut = null;  
	HttpURLConnection conn = null;  
	InputStream inputStream = null;  
	try  
	{  
	    // 建立链接  
	    URL httpUrl=new URL(url);  
	    conn=(HttpURLConnection) httpUrl.openConnection();  
	    //以Post方式提交表单，默认get方式    
	    conn.setDoInput(true);    
            conn.setDoOutput(true);  
	    // post方式不能使用缓存   
	    conn.setUseCaches(false);  
	    //连接指定的资源   
            conn.connect();  
            //获取网络输入流  
	    inputStream=conn.getInputStream();  
	    BufferedInputStream bis = new BufferedInputStream(inputStream);  
	    //判断文件的保存路径后面是否以/结尾  
	    if (!filePath.endsWith("/")) {  
	        filePath += "/";  
            }  
	    //写入到文件（注意文件保存路径的后面一定要加上文件的名称）  
	    fileOut = new FileOutputStream(filePath+fileName);  
	    BufferedOutputStream bos = new BufferedOutputStream(fileOut);  
	           
	    byte[] buf = new byte[4096];  
	    int length = bis.read(buf);  
	    //保存文件  
	    while(length != -1)  
	    {  
                bos.write(buf, 0, length);  
                length = bis.read(buf);  
	    }  
	    bos.close();  
	    bis.close();  
	    conn.disconnect();  
	} catch (Exception e) {  
	    e.printStackTrace();  
	    System.out.println("抛出异常！！");  
	}  
    } 
}
