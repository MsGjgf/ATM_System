package AdminView.CardInfo;

import AdminView.AdminView;
import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DelCardView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel cardIDLabel = new JLabel("卡号",JLabel.RIGHT);
    JComboBox<String> cardID = new JComboBox<String>();
    JLabel statusLabel = new JLabel("卡状态",JLabel.RIGHT);
    JLabel status = new JLabel();
    JLabel savingTypeLabel = new JLabel("存款类型",JLabel.RIGHT);
    JLabel savingType = new JLabel();
    JLabel balanceLabel = new JLabel("余额",JLabel.RIGHT);
    JLabel balance = new JLabel();
    JLabel pwdLabel = new JLabel("密码",JLabel.RIGHT);
    JLabel pwd = new JLabel();
    JLabel personIDLabel = new JLabel("身份证号",JLabel.RIGHT);
    JLabel personID = new JLabel();
    JButton delCardBtn = new JButton("销户");
    DelCardListener delCardListener = new DelCardListener();
    public DelCardView(AdminView adminView){
        super(adminView,"销户界面",true);

        Connection con = GetDBConnection.connectDB("bank","root","123456");
        if (con == null)
            return;
        try {
            Statement sql = con.createStatement();
            ResultSet rs = sql.executeQuery("select cardID FROM cardinfo");
            while (rs.next()){
                cardID.addItem(rs.getString("cardID"));
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        cardID.setSelectedIndex(-1);

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
        jPanel.add(balance);

        pwdLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(pwdLabel);
        pwd.setPreferredSize(new Dimension(200,30));
        jPanel.add(pwd);

        personIDLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(personIDLabel);
        personID.setPreferredSize(new Dimension(200,30));
        jPanel.add(personID);

        jPanel.add(delCardBtn);

        Container contentPane = getContentPane();
        contentPane.add(jPanel);

        //添加监视器
        delCardListener.setDelCardView(this);
        cardID.addItemListener(delCardListener);
        cardID.addActionListener(delCardListener);
        delCardBtn.addActionListener(delCardListener);


        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

