package BusinessLogic;

//表示当前选定的领域
public class CurrentField {
    private static Field currentField = Field.Tech;
    
    public static Field getCurrentField(){
        return currentField;
    }
    
    public static void setCurrentField(Field field){
        currentField = field;
    }
}
