package AdminView.CardInfo;

import AdminView.AdminView;
import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddCardView extends JDialog {
    Connection con = null;
    Statement sql;
    ResultSet rs;
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel savingTypeLabel = new JLabel("存款类型",JLabel.RIGHT);
    JComboBox <String> savingType = new JComboBox<String>();
    JLabel pwdLabel = new JLabel("密码",JLabel.RIGHT);
    JPasswordField pwd = new JPasswordField();
    JLabel personIDLabel = new JLabel("身份证号",JLabel.RIGHT);
    JComboBox <String> personID = new JComboBox<String>();
    JButton addCardBtn = new JButton("开户");
    AddCardListener addCardListener = new AddCardListener();
    public AddCardView(AdminView adminView) throws SQLException {
        super(adminView,"开户界面",true);

        con = GetDBConnection.connectDB("bank","root","123456");
        if (con==null)
            return;
        sql = con.createStatement();
        rs = sql.executeQuery("select savingName from deposit");
        for (int i = 0;rs.next();i++){
            savingType.addItem(rs.getString("savingName"));
        }
        rs = sql.executeQuery("select personID from userinfo");
        for (int i = 0;rs.next();i++){
            personID.addItem(rs.getString("personID"));
        }

        savingTypeLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(savingTypeLabel);
        savingType.setPreferredSize(new Dimension(200,30));
        jPanel.add(savingType);

        pwdLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(pwdLabel);
        pwd.setPreferredSize(new Dimension(200,30));
        jPanel.add(pwd);

        personIDLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(personIDLabel);
        personID.setPreferredSize(new Dimension(200,30));
        jPanel.add(personID);

        jPanel.add(addCardBtn);

        Container contentPane = getContentPane();
        contentPane.add(jPanel);

        //监听器
        addCardListener.setAddCardView(this);
        addCardBtn.addActionListener(addCardListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

