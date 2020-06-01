package UI;

import DataAccess.*;
import BusinessLogic.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.*;

//词汇包管理窗口
public class WordBagManagerDialog extends JDialog {
    
    JPanel pnlWordBag = new JPanel();//科技词库面板
    JPanel pnlBlockedWordBag = new JPanel();//屏蔽词库面板
    
    //科技词库面板上的组件
    JLabel lblWordBag = new JLabel("自定义词库", JLabel.CENTER);
    JTextArea txtWordBag = new JTextArea();
    JScrollPane sclWordBag = new JScrollPane(txtWordBag, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    JTextField txfWord = new JTextField();
    JPanel pnlWordAndWordButtons = new JPanel();
    JPanel pnlWordButtons = new JPanel();
    JButton btnAddWord = new JButton("添加词汇 (+)");
    JButton btnDeleteWord = new JButton("删除词汇 (-)");
    
    //屏蔽词库面板上的组件
    JLabel lblBlockedWordBag = new JLabel("屏蔽词库", JLabel.CENTER);
    JTextArea txtBlockedWordBag = new JTextArea();
    JScrollPane sclBlockedWordBag = new JScrollPane(txtBlockedWordBag, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    JTextField txfBlockedWord = new JTextField();
    JPanel pnlBlockedWordAndBlockedWordButtons = new JPanel();
    JPanel pnlBlockedWordButtons = new JPanel();
    JButton btnBlockedAddWord = new JButton("添加词汇 (+)");
    JButton btnBlockedDeleteWord = new JButton("删除词汇 (-)");
    
    public WordBagManagerDialog(){
        
        setSize(800, 600);
        setTitle("管理词库");
        
        //设置界面基本
        getContentPane().setLayout(new GridLayout(1, 2, 5, 5));
        
        switch(CurrentField.getCurrentField()){
            case Tech:
                setTitle("管理科技词库");
                lblWordBag.setText("科技词库");
                lblBlockedWordBag.setText("科技屏蔽词库");
                break;
            case Entertainment:
                setTitle("管理娱乐词库");
                lblWordBag.setText("娱乐词库");
                lblBlockedWordBag.setText("娱乐屏蔽词库");
                break;
            case Sport:
                setTitle("管理体育词库");
                lblWordBag.setText("体育词库");
                lblBlockedWordBag.setText("体育屏蔽词库");
                break;
            case Custom:
                setTitle("管理自定义词库");
                lblWordBag.setText("自定义词库");
                lblBlockedWordBag.setText("自定义屏蔽词库");
                break;
        }
        
        pnlWordBag.setLayout(new BorderLayout());
        pnlWordBag.add(lblWordBag, BorderLayout.NORTH);
        pnlWordBag.add(sclWordBag, BorderLayout.CENTER);
        pnlWordBag.add(pnlWordAndWordButtons, BorderLayout.SOUTH);
        pnlWordAndWordButtons.setLayout(new GridLayout(2, 1, 0, 0));
        pnlWordAndWordButtons.add(txfWord);
        pnlWordAndWordButtons.add(pnlWordButtons);
        pnlWordButtons.setLayout(new GridLayout(1,2, 0, 0));
        pnlWordButtons.add(btnAddWord);
        pnlWordButtons.add(btnDeleteWord);
        lblWordBag.setPreferredSize(new Dimension(0, 35));
        txfWord.setPreferredSize(new Dimension(0, 35));
        pnlWordButtons.setPreferredSize(new Dimension(0, 35));
        lblWordBag.setFont(new Font("Black", Font.PLAIN, 15));
        txtWordBag.setEditable(false);
        txfWord.setHorizontalAlignment(JTextField.CENTER);
        txtWordBag.setLineWrap(true);//激活自动换行功能 
        txtWordBag.setWrapStyleWord(true);// 激活断行不断字功能
        txtWordBag.setFont(new Font("Black", Font.PLAIN, 15));
        
        pnlBlockedWordBag.setLayout(new BorderLayout());
        pnlBlockedWordBag.add(lblBlockedWordBag, BorderLayout.NORTH);
        pnlBlockedWordBag.add(sclBlockedWordBag, BorderLayout.CENTER);
        pnlBlockedWordBag.add(pnlBlockedWordAndBlockedWordButtons, BorderLayout.SOUTH);
        pnlBlockedWordAndBlockedWordButtons.setLayout(new GridLayout(2, 1, 0, 0));
        pnlBlockedWordAndBlockedWordButtons.add(txfBlockedWord);
        pnlBlockedWordAndBlockedWordButtons.add(pnlBlockedWordButtons);
        pnlBlockedWordButtons.setLayout(new GridLayout(1, 2, 0, 0));
        pnlBlockedWordButtons.add(btnBlockedAddWord);
        pnlBlockedWordButtons.add(btnBlockedDeleteWord);
        lblBlockedWordBag.setPreferredSize(new Dimension(0, 35));
        txfBlockedWord.setPreferredSize(new Dimension(0, 35));
        pnlBlockedWordButtons.setPreferredSize(new Dimension(0, 35));
        lblBlockedWordBag.setFont(new Font("Black", Font.PLAIN, 15));
        txtBlockedWordBag.setEditable(false);
        txfBlockedWord.setHorizontalAlignment(JTextField.CENTER);
        txtBlockedWordBag.setLineWrap(true);//激活自动换行功能 
        txtBlockedWordBag.setWrapStyleWord(true);// 激活断行不断字功能
        txtBlockedWordBag.setFont(new Font("Black", Font.PLAIN, 15));
        
        if(CurrentField.getCurrentField() != Field.Custom){
            txfWord.setEnabled(false);
            btnAddWord.setEnabled(false);
            btnDeleteWord.setEnabled(false);
        }
        
        getContentPane().add(pnlWordBag);
        getContentPane().add(pnlBlockedWordBag);
        
        //刷新两个TextField
        refresh();
        
        //设置事件监听器
        btnAddWord.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    try{
                        if((txfWord.getText().equals("")) || WordBagManager.addCustomWord(txfWord.getText()) == false){
                            Frame frame = new Frame();
                            JOptionPane.showMessageDialog((Component)frame, "加入失败", "Fail", JOptionPane.ERROR_MESSAGE);
                            frame = null;
                        }
                        else{
                            refresh();
                            Frame frame = new Frame();
                            JOptionPane.showMessageDialog((Component)frame, "加入成功", "Success", JOptionPane.DEFAULT_OPTION);
                            frame = null;
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        );
        
        btnDeleteWord.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    try{
                        if(WordBagManager.deleteCustomWord(txfWord.getText()) == false){
                            Frame frame = new Frame();
                            JOptionPane.showMessageDialog((Component)frame, "删除失败", "Fail", JOptionPane.ERROR_MESSAGE);
                            frame = null;
                        }
                        else{
                            refresh();
                            Frame frame = new Frame();
                            JOptionPane.showMessageDialog((Component)frame, "删除成功", "Success", JOptionPane.DEFAULT_OPTION);
                            frame = null;
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        );
        
        btnBlockedAddWord.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    try{
                        if((txfBlockedWord.getText().equals("")) || WordBagManager.addBlockedWord(txfBlockedWord.getText()) == false){
                            Frame frame = new Frame();
                            JOptionPane.showMessageDialog((Component)frame, "加入失败", "Fail", JOptionPane.ERROR_MESSAGE);
                            frame = null;
                        }
                        else{
                            refresh();
                            Frame frame = new Frame();
                            JOptionPane.showMessageDialog((Component)frame, "加入成功", "Success", JOptionPane.DEFAULT_OPTION);
                            frame = null;
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        );
        
        btnBlockedDeleteWord.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    try{
                        if(WordBagManager.deleteBlockedWord(txfBlockedWord.getText()) == false){
                            Frame frame = new Frame();
                            JOptionPane.showMessageDialog((Component)frame, "删除失败", "Fail", JOptionPane.ERROR_MESSAGE);
                            frame = null;
                        }
                        else{
                            refresh();
                            Frame frame = new Frame();
                            JOptionPane.showMessageDialog((Component)frame, "删除成功", "Success", JOptionPane.DEFAULT_OPTION);
                            frame = null;
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        );
        
    }
    
    //刷新自定义词库及屏蔽词库的TextField
    public void refresh() {
        
        //读出文件到词库数组并显示在对应TextArea
        String[] wordBagStrings;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath(CurrentField.getCurrentField())))){
            System.out.println(CurrentField.getCurrentField());
            wordBagStrings = (String[])input.readObject();
            StringBuilder sb = new StringBuilder();
            for(String s : wordBagStrings){
                sb.append(s).append(" ");
            }
            txtWordBag.setText(sb.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        String[] blockedWordBagStrings;
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(blockedFilePath(CurrentField.getCurrentField())))){
            blockedWordBagStrings = (String[])input.readObject();
            StringBuilder sb = new StringBuilder();
            for(String s : blockedWordBagStrings){
                sb.append(s).append(" ");
            }
            txtBlockedWordBag.setText(sb.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        //清空两个TextField
        txfWord.setText("");
        txfBlockedWord.setText("");
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
