package DataAccess;

import BusinessLogic.*;
import java.io.*;

//用于保存用户配置信息
public class Config implements Serializable{
    public String version;//本地词汇包版本号
    public Field field;//当前所选定的领域
    public Config(String version, Field field){
        this.version = version;
        this.field = field;
    }
    
    public static String getVersion(){
        Config config = null;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("./wordBags/config"))){
            config = (Config)input.readObject();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return config.version;
    }
    
    public static void setVersion(String version){
        Config config = null;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("./wordBags/config"))){
            config = (Config)input.readObject();
            config.version = version;
            try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./wordBags/config"))){
                output.writeObject(config);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
   
}
