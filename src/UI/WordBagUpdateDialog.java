package UI;

import BusinessLogic.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//词汇包升级窗口
public class WordBagUpdateDialog extends JDialog {
    private JLabel lblCurrentVersion = new JLabel("", JLabel.CENTER);
    private JLabel lblLatestVersion = new JLabel("", JLabel.CENTER);
    private JLabel lblMessage = new JLabel("", JLabel.CENTER);
    private JButton btn = new JButton();
    
    public WordBagUpdateDialog(String currentVersion, String latestVersion){
        setSize(400, 300);
        setTitle("词库更新");

        getContentPane().setLayout(new GridLayout(4, 1));
        getContentPane().add(lblCurrentVersion);
        getContentPane().add(lblLatestVersion);
        getContentPane().add(lblMessage);
        getContentPane().add(btn);
        
        if(currentVersion.equals(latestVersion)){
            lblCurrentVersion.setText("当前词库版本: " + currentVersion);
            lblLatestVersion.setText("最新词库版本: " + latestVersion);
            lblMessage.setText("未检测到更新");
            btn.setText("好的");
        }
        else{
            lblCurrentVersion.setText("当前词库版本: " + currentVersion);
            lblLatestVersion.setText("最新词库版本: " + latestVersion);
            lblMessage.setText("检测到更新");
            btn.setText("立即更新");
        }
        
        btn.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    if(currentVersion.equals(latestVersion)){
                        dispose();
                    }
                    else{
                        try{
                            SwingUtilities.invokeLater(
                                () -> {
                                    btn.setText("更新中");
                                }
                            );
                            new Thread(() -> {
                                try{
                                    WordBagUpdater.update();
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                }finally{
                                    btn.setText("更新完成");
                                }
                                
                            }).start();
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        );
    }
}
