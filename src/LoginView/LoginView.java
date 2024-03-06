package LoginView;

import javax.swing.*;
import java.awt.*;

public class    LoginView extends JFrame {
    /*******标题******/
    JLabel titleLabel = new JLabel("ATM存取款系统",JLabel.CENTER);
    /*******组件*****/
    SpringLayout springLayout = new SpringLayout();
    JPanel centerPanel = new JPanel(springLayout);
    JLabel cardLabel = new JLabel("卡号：");
    JLabel pwdLabel = new JLabel("密码：");
    JTextField cardFiled = new JTextField();
    JPasswordField pwdFiled = new JPasswordField();
    JLabel rootLabel = new JLabel("权限：");
    JComboBox<String> optionText = new JComboBox<String>();
    JButton loginBtn = new JButton("登录");
    JButton resetBtn = new JButton("重置");
    /********监听器********/
    LoginListener loginListener;
    public LoginView(){
        Container contentPane = getContentPane();

        //设置标题
        titleLabel.setFont(new Font("楷体",Font.PLAIN,40));
        titleLabel.setPreferredSize(new Dimension(0,80));

        //设置组件
        Font centerFont = new Font("楷体",Font.PLAIN,20);
        cardLabel.setFont(centerFont);
        cardFiled.setPreferredSize(new Dimension(200,30));
        pwdLabel.setFont(centerFont);
        pwdFiled.setPreferredSize(new Dimension(200,30));
        rootLabel.setFont(centerFont);
        optionText.addItem("客户");
        optionText.addItem("管理员");
        optionText.setFont(centerFont);
        optionText.setPreferredSize(new Dimension(200,30));
        loginBtn.setFont(centerFont);
        resetBtn.setFont(centerFont);

        //把组件放入面板
        centerPanel.add(cardLabel);
        centerPanel.add(cardFiled);
        centerPanel.add(pwdLabel);
        centerPanel.add(pwdFiled);
        centerPanel.add(rootLabel);
        centerPanel.add(optionText);
        centerPanel.add(loginBtn);
        centerPanel.add(resetBtn);

        //添加监听器
        loginListener = new LoginListener(this);
        loginBtn.addActionListener(loginListener);
        resetBtn.addActionListener(loginListener);

        //添加键盘事件
        loginBtn.addKeyListener(loginListener);

        //设置loginBtn为默认按钮
        getRootPane().setDefaultButton(loginBtn);

        //弹簧布局
        Spring childWidth = Spring.sum(Spring.sum(Spring.width(cardLabel),Spring.width(cardFiled)),Spring.constant(20));
        int offsetX = childWidth.getValue()/2;
        //cardLabel布局
        springLayout.putConstraint(SpringLayout.WEST,cardLabel,-offsetX,SpringLayout.HORIZONTAL_CENTER,centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH,cardLabel,20,SpringLayout.NORTH,centerPanel);
        //cardFiled布局
        springLayout.putConstraint(SpringLayout.WEST,cardFiled,20,SpringLayout.EAST,cardLabel);
        springLayout.putConstraint(SpringLayout.NORTH,cardFiled,0,SpringLayout.NORTH,cardLabel);
        //pwdLabel布局
        springLayout.putConstraint(SpringLayout.EAST,pwdLabel,0,SpringLayout.EAST,cardLabel);
        springLayout.putConstraint(SpringLayout.NORTH,pwdLabel,20,SpringLayout.SOUTH,cardLabel);
        //pwdFiled布局
        springLayout.putConstraint(SpringLayout.WEST,pwdFiled,20,SpringLayout.EAST,pwdLabel);
        springLayout.putConstraint(SpringLayout.NORTH,pwdFiled,0,SpringLayout.NORTH,pwdLabel);
        //root布局
        springLayout.putConstraint(SpringLayout.EAST,rootLabel,0,SpringLayout.EAST,pwdLabel);
        springLayout.putConstraint(SpringLayout.NORTH,rootLabel,50,SpringLayout.NORTH,pwdLabel);
        //optionText布局
        springLayout.putConstraint(SpringLayout.WEST,optionText,20,SpringLayout.EAST,pwdLabel);
        springLayout.putConstraint(SpringLayout.NORTH,optionText,50,SpringLayout.NORTH,pwdLabel);
        //loginBtn布局
        springLayout.putConstraint(SpringLayout.WEST,loginBtn,50,SpringLayout.WEST,pwdLabel);
        springLayout.putConstraint(SpringLayout.NORTH,loginBtn,100,SpringLayout.SOUTH,pwdLabel);
        //adminBtn布局
        springLayout.putConstraint(SpringLayout.WEST,resetBtn,50,SpringLayout.EAST,loginBtn);
        springLayout.putConstraint(SpringLayout.NORTH,resetBtn,0,SpringLayout.NORTH,loginBtn);

        contentPane.add(titleLabel,BorderLayout.NORTH);
        contentPane.add(centerPanel,BorderLayout.CENTER);

        setTitle("ATM存取款系统");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    /**执行入口**/
    public static void main(String[] args) {
        new LoginView();
    }
}
