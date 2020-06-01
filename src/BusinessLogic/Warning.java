package BusinessLogic;

import java.awt.*;
import javax.swing.*;

//用于在特定情况下显示警告信息
public class Warning {
    public static void NoNetworkWarning(){
        Frame frame = new Frame();
        JOptionPane.showMessageDialog((Component)frame, "请检查您的网络设置", "网络异常", JOptionPane.ERROR_MESSAGE);
        frame = null;
    }
    
    public static void NoPermissionWarning(){
        Frame frame = new Frame();
        JOptionPane.showMessageDialog((Component)frame, "如您使用Windows系统，请\"以管理员身份运行\"此应用，\n"
                + "否则应用功能不能正常运行", "权限不足", JOptionPane.ERROR_MESSAGE);
        frame = null;
    }
}
