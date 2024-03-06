package UserView;

import javax.swing.*;
import java.awt.*;

public class UserView extends JFrame {
    JPanel leftPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JButton cunkuanBtn = new JButton("存  款");
    JButton qukuanBtn = new JButton("取  款");
    JButton zhuanzhangBtn = new JButton("转  账");
    JLabel centerLabel = new JLabel();
    JButton balanceBtn = new JButton("查询余额");
    JButton tradeBtn = new JButton("查询交易");
    JButton exitBtn = new JButton("退出账户");
    UserListener userViewListener = new UserListener();
    public UserView(String s){
        //获取卡号
        String cardID = s;
        Container contentPane = getContentPane();

        Font font = new Font("微软雅黑",Font.BOLD,16);
        //放置左边的组件
        cunkuanBtn.setFont(font);
        leftPanel.add(cunkuanBtn);
        qukuanBtn.setFont(font);
        leftPanel.add(qukuanBtn);
        zhuanzhangBtn.setFont(font);
        leftPanel.add(zhuanzhangBtn);
        contentPane.add(leftPanel,BorderLayout.WEST);

        //放在中间的组件
        centerLabel.setText("欢迎卡号：\""+cardID+"\"");
        centerLabel.setFont(new Font("楷体",Font.BOLD,30));
        centerPanel.add(centerLabel);
        contentPane.add(centerPanel,BorderLayout.NORTH);

        //放置右边的组件
        balanceBtn.setFont(font);
        rightPanel.add(balanceBtn);
        tradeBtn.setFont(font);
        rightPanel.add(tradeBtn);
        exitBtn.setFont(font);
        rightPanel.add(exitBtn);
        contentPane.add(rightPanel,BorderLayout.EAST);

        //添加监听器
        userViewListener.setUserView(this);
        cunkuanBtn.addActionListener(userViewListener);
        qukuanBtn.addActionListener(userViewListener);
        zhuanzhangBtn.addActionListener(userViewListener);
        balanceBtn.addActionListener(userViewListener);
        tradeBtn.addActionListener(userViewListener);
        exitBtn.addActionListener(userViewListener);

        setTitle("ATM存取款系统-客户界面");
        setSize(600,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
}
