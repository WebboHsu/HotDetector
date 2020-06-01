package DataAccess;

import BusinessLogic.CurrentField;
import BusinessLogic.Field;
import BusinessLogic.Warning;
import java.io.*;
import java.util.*;

//用于编辑词汇包
public class WordBagManager {
    
    //加入自定义词汇： 如果该词汇为空or词库已经有该词汇，返回false；否则加入该词汇，返回true。   
    public static boolean addCustomWord(String word) throws IOException, ClassNotFoundException{
        //读出文件到字符数组
        String[] wordBagStrings;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("./wordBags/customWordBag"))){
            wordBagStrings = (String[])input.readObject();
        }
        //修改这个字符数组
        List<String> list = Arrays.asList(wordBagStrings);
        List<String> tmpList = new ArrayList<String>(list);
        if(word.equals("") || tmpList.contains(word)){
            return false;
        }
        tmpList.add(word);
        wordBagStrings = (String[])tmpList.toArray(new String[tmpList.size()]);
        //把这个字符数组写回文件
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./wordBags/customWordBag"))){
            output.writeObject(wordBagStrings);
        }catch(Exception ex){
            Warning.NoPermissionWarning();
            return false;
        }
        return true;
    }
    
    //删除自定义词汇： 如果词汇包中没有该词汇，返回false；否则删除该词汇，返回true。
    public static boolean deleteCustomWord(String word) throws IOException, ClassNotFoundException{
        //读出文件到字符数组
        String[] wordBagStrings;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("./wordBags/customWordBag"))){
            wordBagStrings = (String[])input.readObject();
        }
        //修改这个字符数组
        List<String> list = Arrays.asList(wordBagStrings);
        List<String> tmpList = new ArrayList<String>(list);
        if(!tmpList.contains(word)){
            return false;
        }
        tmpList.remove(word);
        wordBagStrings = (String[])tmpList.toArray(new String[tmpList.size()]);
        //把这个字符数组写回文件
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./wordBags/customWordBag"))){
            output.writeObject(wordBagStrings);
        }catch(Exception ex){
            Warning.NoPermissionWarning();
            return false;
        }
        return true;
    }
    
    //加入屏蔽词汇：如果该词汇为空or屏蔽词库已有该词汇，返回false；否则加入该词汇，返回true。
    public static boolean addBlockedWord(String word) throws IOException, ClassNotFoundException{
        //读出文件到屏蔽词库数组
        String[] blockedWordBagStrings;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath(CurrentField.getCurrentField())))){
            blockedWordBagStrings = (String[])input.readObject();
        }
        //修改这个屏蔽词库数组
        List<String> list = Arrays.asList(blockedWordBagStrings);
        List<String> tmpList = new ArrayList<String>(list);
        if(word.equals("") || tmpList.contains(word)){
            return false;
        }
        tmpList.add(word);
        blockedWordBagStrings = (String[])tmpList.toArray(new String[tmpList.size()]);
        //把屏蔽词库数组写回文件
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath(CurrentField.getCurrentField())))){
            output.writeObject(blockedWordBagStrings);
        }catch(Exception ex){
            Warning.NoPermissionWarning();
            return false;
        }
        return true;
    }
    
    //删除屏蔽词汇：如果屏蔽词库中没有该词汇，返回false；否则删除该词汇，返回true。
    public static boolean deleteBlockedWord(String word) throws IOException, ClassNotFoundException{
        //读出文件到字符数组
        String[] blockedWordBagStrings;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath(CurrentField.getCurrentField())))){
            blockedWordBagStrings = (String[])input.readObject();
        }
        //修改这个字符数组
        List<String> list = Arrays.asList(blockedWordBagStrings);
        List<String> tmpList = new ArrayList<String>(list);
        if(!tmpList.contains(word)){
            return false;
        }
        tmpList.remove(word);
        blockedWordBagStrings = (String[])tmpList.toArray(new String[tmpList.size()]);
        //把这个字符数组写回文件
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath(CurrentField.getCurrentField())))){
            output.writeObject(blockedWordBagStrings);
        }catch(Exception ex){
            Warning.NoPermissionWarning();
            return false;
        }
        return true;
    }
    
    private static String filePath(Field field){
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
