package BusinessLogic;

import DataAccess.*;
import UI.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        //最初的写入词汇包文件
        InitWordBags.initWordBags();
        
        //加载配置信息
        Config config = null;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("./wordBags/config"))){
            config = (Config)input.readObject();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        CurrentField.setCurrentField(config.field);
        
        //打开窗口
        MainFrame mainFrame = new MainFrame();
        mainFrame.addWindowListener(new MyWindowListener());
        mainFrame.setVisible(true);
        
        //检查更新
        WordBagUpdater.checkUpdate();
    }
    
}

//爬虫测试类
class DetectorTest{
    public static void test() throws IOException, ClassNotFoundException{
        
        
        //显示相关微博热搜条目
        System.out.println("相关微博热搜条目:");
        java.util.List<HotItem> weiboSelectedItems = Detector.weiboDetector();
        for(HotItem s : weiboSelectedItems){
            System.out.println(s);
        }
        
        System.out.println();
        
        //显示相关知乎热榜
        System.out.println("相关知乎热榜条目");
        java.util.List<HotItem> zhihuSelectedItems = Detector.zhihuDetector();
        for(HotItem s : zhihuSelectedItems){
            System.out.println(s);
        }
        
        System.out.println();
        
        //显示相关百度实时热点条目
        System.out.println("相关百度实时热点条目");
        java.util.List<HotItem> baiduSelectedItems = Detector.baiduDetector();
        for(HotItem s : baiduSelectedItems){
            System.out.println(s);
        }
        
        
    }
}

//最初的词汇包写入
class InitWordBags{
//备份词库
    private static String[] techWordBag = {
                //企业名称
    	        "苹果","三星","华为","荣耀","小米","红米","华米","OPPO",
    	        "vivo","魅族","努比亚","锤子","阿里","腾讯","百度","微软",
    	        "谷歌","台积电","中兴","联想","中芯","海信","Facebook","亚马逊",
    	        "美团","当当","饿了么","一加","字节跳动","京东","高通","英特尔",
    	        "英伟达","IBM","特斯拉","蔚来","小鹏","任天堂","索尼","格力",
    	        "中国移动","联通","电信","摩托罗拉","米家","realme",
    	        "网易","蚂蚁金服","Space","滴滴","大疆","菜鸟","旷视","海康",
    	        "拼多多","乐视",
    	        
    	               
    	        //科技人物
    	        "乔布斯","库克","马斯克","李国庆","余渝","雷军","林斌","卢伟冰",
    	        "常程","刘作虎","任正非","余承东","赵明","马云","蒋凡",
    	        "张勇","扎克伯格","贝佐斯","贝索斯","刘强东","董明珠","罗永浩",
    	        "贾跃亭",
    	        
    	        
    	        //科技产品
    	        "iPhone","Mac","iPad","Watch","AirPod","HomePod","iOS",
    	        "安卓","Android","鸿蒙","小爱","支付宝","nova","HMS","Kindle",
    	        "比特","微信","QQ","Switch","抖音","快手","B站","骁龙",
    	        "钉钉","Linux","Windows","Siri","Surface","C++","Python",
    	        "Java","Swift",
    	        
    	        
    	        //其他短语
    	        "科技","科学","智能","手机","手环","电脑","电视","智慧屏",
    	        "软件","硬件","计算机","电子","航天","卫星","硅谷","芯",
    	        "VR","虚拟现实","AR","增强现实","开发者","AI","区块链","APP",
    	        "UI","机器学习","屏幕","CPU","APP","PC","脑机","屏幕",
    	        "IT","电动","网络","互联","工信部","运营商","广电","5G",
    	        "4G","信号","编程","专利","星链","VPN","程序","基站",
    	        "通信","设备","折叠屏","充电","物联网","旗舰","数据","编程",
    	        "com","自动驾驶","无人","融资","服务器","设计","合伙人","高管",
    	        "设计","用户","代码","源码","开源","宇宙","火箭","太空",
    	        "IP","Pro","业绩","下载","黑客","极客",

    };
        
        //备份屏蔽词库
    private static String[] blockedTechWordBag = {};
    
    private static String[] entertainmentWordBag = {
        "娱乐圈","虞书欣","何炅","陈芊芊","王一博","易烊千玺","谢娜","伊能静","鹿晗","郑云龙","仝卓","吴宣仪","杨幂",
"饭圈","歌手","演员","卫视","娱乐","电视剧","影视剧","主角","配角","定档","翻拍","综艺","明星",
"第一季","第二季","第三季","第四季","第五季","第六季",
"奔跑吧","青你","青春有你","最强大脑","声入人心","好声音",
"校草","校花","CP","分手","演技","恋情",
"李宇春","陈小春","张根硕","何猷君","应采儿","王一博","肖战","易烊千玺",
"严浩翔","王俊凯","罗云熙","翟潇闻","蔡徐坤","迪丽热巴","周震南","李现","朱一龙",
"范丞丞","华晨宇","任嘉伦","黄子韬","赵丽颖","朱正廷","王源","黄晓明","范冰冰","谢娜",
"张云雷","张杰","李易峰","孟美岐","李荣浩","杨紫","鞠婧祎","吴宣仪","邓超","薛之谦",
"沈腾","刘亦菲","陈赫","周冬雨","江疏影","毛不易","杨超越","张若昀",
    };
    
    private static String[] blockedEntertainmentWordBag = {};
    
    private static String[] sportWordBag = {
        "体育","DOTA","LoL","LOL","英雄联盟","王者荣耀","守望先锋","CSGO","阴阳师",
"iG","RNG",
"排位","转会","退役","联赛","赛季","MVP",
"篮球","网球","足球","排球","乒乓球","高尔夫","手游","太极拳",
"NBA","CBA","英超","中超","暴雪",
"詹姆斯","科比","乔丹","威少","奥登",
"骑士","湖人","火箭",
"跑步","田径","跳高","跨栏","跳远","接力","拔河","跳绳","运动",
"奥运会","亚运会","锦标赛","运动会",

    };
    
    private static String[] blockedSportWordBag = {};
    
    private static String[] customWordBag = {};
    
    private static String[] blockedCustomWordBag = {};
    
    public static void initWordBags() {
        try{
              
            File f1 = new File("./wordBags/techWordBag");
            if(!f1.exists()) {
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f1))){
                    output.writeObject(techWordBag);
                }
            }
        
            File f2 = new File("./wordBags/blockedTechWordBag");
            if(!f2.exists()) {
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f2))){
                    output.writeObject(blockedTechWordBag);
                }
            }

            File f3 = new File("./wordBags/entertainmentWordBag");
            if(!f3.exists()) {
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f3))){
                    output.writeObject(entertainmentWordBag);
                }
            }

            File f4 = new File("./wordBags/blockedEntertainmentWordBag");
            if(!f4.exists()) {
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f4))){
                    output.writeObject(blockedEntertainmentWordBag);
                }
            }

            File f5 = new File("./wordBags/sportWordBag");
            if(!f5.exists()) {
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f5))){
                    output.writeObject(sportWordBag);
                }
            }
        
            File f6 = new File("./wordBags/blockedSportWordBag");
            if(!f6.exists()) {
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f6))){
                    output.writeObject(blockedSportWordBag);
                }
            }

            File f7 = new File("./wordBags/customWordBag");
            if(!f7.exists()) {
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f7))){
                    output.writeObject(customWordBag);
                }
            }

            File f8 = new File("./wordBags/blockedCustomWordBag");
            if(!f8.exists()) {
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f8))){
                    output.writeObject(blockedCustomWordBag);
                }
            }
            
            //初始化配置信息
            File fConfig = new File("./wordBags/config");
            if(!fConfig.exists()){
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fConfig))){
                    output.writeObject(new Config("6.0", Field.Tech));
                }
            }
            
        }catch(IOException ex){
            Warning.NoPermissionWarning();
            ex.printStackTrace();
        }
    }
}

class MyWindowListener extends WindowAdapter{
    @Override
    public void windowClosing(WindowEvent e){
        super.windowClosing(e);
    }
}