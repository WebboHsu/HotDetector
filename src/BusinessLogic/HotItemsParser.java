package BusinessLogic;

import DataAccess.*;
import java.io.*;
import java.util.*;

//用于从爬虫获取到的热门条目中筛选出与特定领域有关的条目
public class HotItemsParser {
    public static List<HotItem> hotItemsParser(List<HotItem> items) throws ClassNotFoundException{
        //读出文件到字符数组
        String[] wordBag = null;
        String[] blockedWordBag = null;
        String filePath = filePath(CurrentField.getCurrentField());
        String blockedFilePath = blockedFilePath(CurrentField.getCurrentField());
        
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))){
            wordBag = (String[])input.readObject();

        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(blockedFilePath))){
            blockedWordBag = (String[])input.readObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        List<HotItem> selectedItems = new ArrayList<>();
        
        //与词汇包比对
        for(HotItem s: items){
            String content = s.getContent();
            for(String word : wordBag){
                if(content.toLowerCase().contains(word.toLowerCase())){
                    int flag = 0;
                    for(String blockedWord : blockedWordBag){
                        if(content.toLowerCase().contains(blockedWord.toLowerCase())){
                                flag = 1;
                        }
                    }
                    if(flag == 0){
                        selectedItems.add(s);
                        break;
                    }
                }
            }
        }
        
        return selectedItems;
    }
    
    private static String filePath(Field field){
        switch(field){
            case Tech:
                return "./wordBags/techWordBag";
            case Entertainment:
                return "./wordBags/entertainmentWordBag";
            case Sport:
                return "./wordBags/sportWordBag";
            case Custom:
                return "./wordBags/customWordBag";
        }
        return "";
    }
    
    private static String blockedFilePath(Field field){
        switch(field){
            case Tech:
                return "./wordBags/blockedTechWordBag";
            case Entertainment:
                return "./wordBags/blockedEntertainmentWordBag";
            case Sport:
                return "./wordBags/blockedSportWordBag";
            case Custom:
                return "./wordBags/blockedCustomWordBag";
        }
        return "";
    }
}
