package UI;

import BusinessLogic.*;
import DataAccess.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;

//主窗口
public class MainFrame extends JFrame {
    
    private JPanel pnlTitle = new JPanel(new GridLayout(1, 3, 5, 5));
    private JPanel pnlContent = new JPanel(new GridLayout(1, 3, 5, 5));
    private JPanel pnlControl = new JPanel(new GridLayout(1, 2, 0, 0));
    
    private JLabel lblRdo = new JLabel("领域:", JLabel.CENTER);
    private JRadioButton rdoTech = new JRadioButton("科技");
    private JRadioButton rdoSport = new JRadioButton("体育");
    private JRadioButton rdoEntertainment = new JRadioButton("娱乐");
    private JRadioButton rdoCustom = new JRadioButton("自定义");
    private JPanel pnlWordBag = new JPanel(new GridLayout(2, 1, 5, 5));
    private ButtonGroup grpRdo = new ButtonGroup();
    private JPanel pnlRdo = new JPanel(new GridLayout(1, 6, 5, 5));
    private JPanel pnlHelpAndManage = new JPanel(new GridLayout(1, 3, 5, 5));
    
    private JLabel lblWeibo = new JLabel("热搜   ", JLabel.CENTER);
    private JLabel lblZhihu = new JLabel(" 热榜 ", JLabel.CENTER);
    private JLabel lblBaidu = new JLabel(" 实时热点  ", JLabel.CENTER);
    
    //图片
    ImageIcon weiboImage = new ImageIcon("imgs/weibo.png");
    ImageIcon zhihuImage = new ImageIcon("imgs/zhihu.png");
    ImageIcon baiduImage = new ImageIcon("imgs/baidu.png");
    ImageIcon signalImage = new ImageIcon("imgs/signal.png");

    private JEditorPane pnlWeibo = new JEditorPane();
    private JEditorPane pnlZhihu = new JEditorPane();
    private JEditorPane pnlBaidu = new JEditorPane();
    
    
    private JScrollPane scrWeibo = new JScrollPane(pnlWeibo, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private JScrollPane scrZhihu = new JScrollPane(pnlZhihu, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private JScrollPane scrBaidu = new JScrollPane(pnlBaidu, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    
    private JButton btnHelp = new JButton("帮助");
    private JButton btnRefresh = new JButton(" 探测 ");
    private JButton btnManage = new JButton("管理词库");
    private JButton btnCheck = new JButton("检查词库更新");
    
    boolean flag = true;
    
    //构造方法
    public MainFrame(){
        initComponents();
        initEventListeners();
    }
    
    //初始化组件外观
    private void initComponents(){
        
        setTitle("全网热门探测仪");
        setSize(1200, 750);
        getContentPane().add(pnlTitle, BorderLayout.NORTH);
        getContentPane().add(pnlContent, BorderLayout.CENTER);
        getContentPane().add(pnlControl, BorderLayout.SOUTH);
        
        /*
        JPanel jp = (JPanel)getContentPane();
        JRootPane jp1 = (JRootPane)getRootPane();
        jp.setOpaque(false);
        jp1.setOpaque(false);
        */
        
        pnlTitle.setPreferredSize(new Dimension(0, 40));
        pnlTitle.add(lblWeibo);
        pnlTitle.add(lblZhihu);
        pnlTitle.add(lblBaidu);
        
        lblWeibo.setFont(new Font("Black", Font.BOLD, 20));
        lblZhihu.setFont(new Font("Black", Font.BOLD, 20));
        lblBaidu.setFont(new Font("Black", Font.BOLD, 20));
        lblWeibo.setForeground(Color.DARK_GRAY);
        lblZhihu.setForeground(Color.DARK_GRAY);
        lblBaidu.setForeground(Color.DARK_GRAY);
        lblRdo.setFont(new Font("Black", Font.BOLD, 18));
        rdoTech.setFont(new Font("Black", Font.PLAIN, 18));
        rdoEntertainment.setFont(new Font("Black", Font.PLAIN, 18));
        rdoSport.setFont(new Font("Black", Font.PLAIN, 18));
        rdoCustom.setFont(new Font("Black", Font.PLAIN, 18));
        btnHelp.setFont(new Font("Black", Font.ITALIC,15));
        btnCheck.setFont(new Font("Black", Font.ITALIC,15));
        btnRefresh.setFont(new Font("Black", Font.BOLD,20));
        btnManage.setFont(new Font("Black", Font.ITALIC,15));
        btnHelp.setBorderPainted(false);
        btnManage.setBorderPainted(false);
        btnCheck.setBorderPainted(false);
        btnRefresh.setForeground(Color.DARK_GRAY);
        btnRefresh.setOpaque(true);
        btnRefresh.setBorderPainted(true);
        btnHelp.setForeground(Color.DARK_GRAY);
        btnManage.setForeground(Color.DARK_GRAY);
        btnCheck.setForeground(Color.DARK_GRAY);
        
        pnlContent.setPreferredSize(new Dimension(0, 600));
        pnlContent.add(scrWeibo);
        pnlContent.add(scrZhihu);
        pnlContent.add(scrBaidu);
        

        pnlWeibo.setEditable(false);
        pnlWeibo.setContentType("text/html");
        pnlZhihu.setEditable(false);
        pnlZhihu.setContentType("text/html");
        pnlBaidu.setEditable(false);
        pnlBaidu.setContentType("text/html");
        
        
        
        pnlControl.setPreferredSize(new Dimension(0, 100));
        pnlControl.add(pnlWordBag);
        pnlControl.add(btnRefresh);
        grpRdo.add(rdoTech);
        grpRdo.add(rdoEntertainment);
        grpRdo.add(rdoSport);
        grpRdo.add(rdoCustom);
        pnlRdo.add(lblRdo);
        pnlRdo.add(rdoTech);
        pnlRdo.add(rdoEntertainment);
        pnlRdo.add(rdoSport);
        pnlRdo.add(rdoCustom);
        pnlWordBag.add(pnlRdo);
        pnlWordBag.add(pnlHelpAndManage);
        pnlHelpAndManage.add(btnHelp);
        pnlHelpAndManage.add(btnManage);
        pnlHelpAndManage.add(btnCheck);
        
        switch(CurrentField.getCurrentField()){
            case Tech:
                rdoTech.setSelected(true);
                btnRefresh.setText(" 探测科技热门 ");
                break;
            case Entertainment:
                rdoEntertainment.setSelected(true);
                btnRefresh.setText(" 探测娱乐热门 ");
                break;
            case Sport:
                rdoSport.setSelected(true);
                btnRefresh.setText(" 探测体育热门 ");
                break;
            case Custom:
                rdoCustom.setSelected(true);
                btnRefresh.setText(" 探测自定义热门 ");
                break;
        }
        
        weiboImage.setImage(weiboImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        lblWeibo.setIcon(weiboImage);
        zhihuImage.setImage(zhihuImage.getImage().getScaledInstance(33, 33, Image.SCALE_SMOOTH));
        lblZhihu.setIcon(zhihuImage);
        baiduImage.setImage(baiduImage.getImage().getScaledInstance(33, 33, Image.SCALE_SMOOTH));
        lblBaidu.setIcon(baiduImage);
        signalImage.setImage(signalImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        btnRefresh.setIcon(signalImage);
        
        //事件响应
        lblWeibo.addMouseListener(
            new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                    URI uri;
                    try {
                        uri = new URI("https://s.weibo.com/top/summary");
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(uri);
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblWeibo.setText("点击以访问");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblWeibo.setText("热搜   ");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        lblZhihu.addMouseListener(
            new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                    URI uri;
                    try {
                        uri = new URI("https://www.zhihu.com/billboard");
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(uri);
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblZhihu.setText("点击以访问");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblZhihu.setText(" 热榜 ");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        lblBaidu.addMouseListener(
            new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                    URI uri;
                    try {
                        uri = new URI("http://top.baidu.com/buzz?b=1");
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(uri);
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblBaidu.setText("点击以访问");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblBaidu.setText(" 实时热点  ");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        btnRefresh.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    flag = false;
                    try{
                        SwingUtilities.invokeLater(
                            () -> {
                                btnRefresh.setIcon(null);
                                btnRefresh.setText(("探测中"));
                            }
                        );
                        new Thread(()->{
                            try{
                                refresh();
                                flag = true;
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }finally{
                                try{
                                    Thread.sleep(300);
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                }
                                switch(CurrentField.getCurrentField()){
                                    case Tech:
                                        btnRefresh.setText(" 探测科技热门 ");
                                        break;
                                    case Entertainment:
                                        btnRefresh.setText(" 探测娱乐热门 ");
                                        break;
                                    case Sport:
                                        btnRefresh.setText(" 探测体育热门 ");
                                        break;  
                                    case Custom:
                                        btnRefresh.setText(" 探测自定义热门 ");
                                        break; 
                                }
                                btnRefresh.setIcon(signalImage);
                            }
                        }).start();
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
        });
        
        btnHelp.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    JOptionPane.showMessageDialog(null, helpString, "帮助", JOptionPane.DEFAULT_OPTION );
                }
            }
        );
        
        btnManage.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    new WordBagManagerDialog().setVisible(true);
                }
            }
        );
        
        btnCheck.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    if(WordBagUpdater.checkUpdate() == false){
                        JOptionPane.showMessageDialog(null, "词库已经是最新版本(" + Config.getVersion() + ")", "检查词库更新", JOptionPane.DEFAULT_OPTION );
                    }
                }
            }
        );
        
        rdoTech.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    CurrentField.setCurrentField(Field.Tech);
                    btnRefresh.setText(" 探测科技热门 ");
                }
            }
        );
        
        rdoEntertainment.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    CurrentField.setCurrentField(Field.Entertainment);
                    btnRefresh.setText(" 探测娱乐热门 ");
                }
            }
        );
        
        rdoSport.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    CurrentField.setCurrentField(Field.Sport);
                    btnRefresh.setText(" 探测体育热门 ");
                }
            }
        );
        
        rdoCustom.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    CurrentField.setCurrentField(Field.Custom);
                    btnRefresh.setText(" 探测自定义热门 ");
                }
            }
        );
        
        pnlBaidu.addHyperlinkListener(
            new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        Desktop desktop = Desktop.getDesktop();
                        try{
                            URI uri = new URI(e.getURL().toString());
                            desktop.browse(uri);
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            });
        
        pnlZhihu.addHyperlinkListener(
            new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        Desktop desktop = Desktop.getDesktop();
                        try{
                            URI uri = new URI(e.getURL().toString());
                            desktop.browse(uri);
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            });
        
        pnlWeibo.addHyperlinkListener(
            new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        Desktop desktop = Desktop.getDesktop();
                        try{
                            URI uri = new URI(e.getURL().toString());
                            desktop.browse(uri);
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            });
    }
    
    
    //初始化组件事件监听器
    private void initEventListeners(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    //刷新内容
    private void refresh() throws IOException, ClassNotFoundException{

        StringBuilder sb = new StringBuilder();
        java.util.List<HotItem> weiboList = Detector.weiboDetector();
        sb.append("<html><body>");
        for(HotItem item : weiboList){
            sb.append("<a href=" + item.getUrlString() + ">"+"<font color=\"black\" face=\"Arial\" size=\"6\">" 
                    + item.getRank() + " " + item.getContent() + "</font><br><br></a>");
        }
        sb.append("</body></html>");
        pnlWeibo.setText(sb.toString());
        
        sb = new StringBuilder();
        java.util.List<HotItem> zhihuList = Detector.zhihuDetector();
        sb.append("<html><body>");
        for(HotItem item : zhihuList){
            /*
            sb.append("<table border=0><tr>"
                    + "<td><a href=" + item.getUrlString() + ">"
                    + "<font color=\"black\" face=\"Arial\" size=\"6\">" + item.getRank() + " " + item.getContent() + "</font><br><br></a></td></tr></table>");
                    //+ "<td><img alt='Bad' img src='https://pic3.zhimg.com/80/v2-75bed3f3c13bdec247ac2b6e5c856869_720w.jpg'></td></tr>");
            */
            int width = (int)(0.5 * (double)pnlZhihu.getWidth());
            if(item.getUrlImgString() != ""){
                sb.append("<a href=" + item.getUrlString() + ">" +"<font color=\"black\" face=\"Arial\" size=\"6\">"
                        + item.getRank() + " " + item.getContent() + "</font><br><br></a>"
                        + "<center><table border=0><tr><td><center><a href=" + item.getUrlImgString() + "><img src='" + item.getUrlImgString() + "'"
                                + " width=120 height=auto></a></center><td><tr></table></center>");
            }
            else{
                sb.append("<a href=" + item.getUrlString() + ">" +"<font color=\"black\" face=\"Arial\" size=\"6\">"
                    + item.getRank() + " " + item.getContent() + "</font><br><br></a>");
            }
        }
        sb.append("</body></html>");
        pnlZhihu.setText(sb.toString());

        sb = new StringBuilder();
        java.util.List<HotItem> baiduList = Detector.baiduDetector();
        sb.append("<html><body>");
        for(HotItem item : baiduList){
            sb.append("<a href=" + item.getUrlString() + ">" +"<font color=\"black\" face=\"Arial\" size=\"6\">"
                    + item.getRank() + " " + item.getContent() + "</font><br><br></a>");
        }
        sb.append("</body></html>");
        pnlBaidu.setText(sb.toString());
        
    }
    
    private String helpString = 
            "欢迎您使用\"全网热门探测仪\"!\n"
            + "这是一款帮您自动提取微博热搜、知乎热榜和百度实时热点条目中与特定领域（科技、娱乐、体育、自定义）相关内容的实用App。\n\n"
            + "点击\"探测科技/娱乐/体育/自定义热门\"按钮即可开始探测。\n\n"
            + "对每一个领域，都有一个领域词库、一个领域屏蔽词库。\n"
            + "在探测过程中，本应用会将网络热门条目自动与所选中领域的词库对照，\n"
            + "含有领域词库中词汇且不含领域屏蔽词库中词汇的，会被认为与该领域相关；\n"
            + "点击\"管理词库\"按钮即可编辑词库。\n\n"
            + "如您使用的是Windows系统，请务必以管理员身份运行此应用以保证功能正常。\n\n"
            + "本应用由徐为伯、赵泠然、谢一平共同开发。\n"
            + "如有任何问题，欢迎发送邮件到webbohsu@126.com来咨询。";
}
