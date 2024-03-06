package AdminView.CardInfo;

import AdminView.AdminView;
import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdCardView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel cardIDLabel = new JLabel("卡号",JLabel.RIGHT);
    JComboBox<String> cardID = new JComboBox<String>();
    JLabel statusLabel = new JLabel("卡状态",JLabel.RIGHT);
    JComboBox<String> status = new JComboBox<String>();
    JLabel savingTypeLabel = new JLabel("存款类型",JLabel.RIGHT);
    JComboBox<String> savingType = new JComboBox<String>();
    JLabel balanceLabel = new JLabel("余额",JLabel.RIGHT);
    JTextField balance = new JTextField();
    JLabel pwdLabel = new JLabel("密码",JLabel.RIGHT);
    JTextField pwd = new JTextField();
    JButton updCardBtn = new JButton("修改");
    JButton rootBtn = new JButton("开启root");
    UpdCardListener updCardListener = new UpdCardListener();
    public UpdCardView(AdminView adminView){
        super(adminView,"修改银行卡信息界面",true);

        Connection con = null;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank","root","123456");
        if (con==null)
            return;
        try {
            sql = con.createStatement();
            rs = sql.executeQuery("select cardID from cardinfo");
            for (int i = 0;rs.next();i++){
                cardID.addItem(rs.getString("cardID"));
            }
            cardID.setSelectedIndex(-1);
            status.addItem("1");
            status.addItem("0");
            rs = sql.executeQuery("select savingName from deposit");
            for (int i = 0;rs.next();i++){
                savingType.addItem(rs.getString("savingName"));
            }
            con.close();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        cardIDLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(cardIDLabel);
        cardID.setPreferredSize(new Dimension(200,30));
        jPanel.add(cardID);

        statusLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(statusLabel);
        status.setPreferredSize(new Dimension(200,30));
        jPanel.add(status);

        savingTypeLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(savingTypeLabel);
        savingType.setPreferredSize(new Dimension(200,30));
        jPanel.add(savingType);

        balanceLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(balanceLabel);
        balance.setPreferredSize(new Dimension(200,30));

        /**因为销户需要清空账户余额，若管理员需要修改账户余额，则需要验证管理员登录密码，以开启最高权限root**/
        balance.setEnabled(false);
        jPanel.add(balance);

        pwdLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(pwdLabel);
        pwd.setPreferredSize(new Dimension(200,30));
        jPanel.add(pwd);

        jPanel.add(updCardBtn);
        jPanel.add(rootBtn);

        Container contentPane = getContentPane();
        contentPane.add(jPanel);

        //添加监视器
        updCardListener.setUpdCardView(this);
        cardID.addItemListener(updCardListener);
        cardID.addActionListener(updCardListener);
        updCardBtn.addActionListener(updCardListener);
        rootBtn.addActionListener(updCardListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

