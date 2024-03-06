package AdminView.UserInfo;

import AdminView.AdminView;
import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DelUserView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel personIDLabel = new JLabel("身份证号：",JLabel.RIGHT);
    JComboBox<String> personID = new JComboBox<String>();
    JLabel nameLabel = new JLabel("姓名：",JLabel.RIGHT);
    JLabel name = new JLabel();
    JLabel phoneLabel = new JLabel("电话号码：",JLabel.RIGHT);
    JLabel phone = new JLabel();
    JLabel addressLabel = new JLabel("家庭住址：",JLabel.RIGHT);
    JLabel address = new JLabel();
    JButton delUserBtn = new JButton("删除客户信息");
    DelUserListener delUserListener = new DelUserListener();
    public DelUserView(AdminView adminView){
        super(adminView,"销户界面",true);

        Connection con = GetDBConnection.connectDB("bank","root","123456");
        if (con == null)
            return;
        try {
            Statement sql = con.createStatement();
            ResultSet rs = sql.executeQuery("select personID FROM userinfo");
            while (rs.next()){
                personID.addItem(rs.getString("personID"));
            }
        }
        catch (SQLException ex){}
        personID.setSelectedIndex(-1);

        personIDLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(personIDLabel);
        personID.setPreferredSize(new Dimension(200,30));
        jPanel.add(personID);

        nameLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(nameLabel);
        name.setPreferredSize(new Dimension(200,30));
        jPanel.add(name);

        phoneLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(phoneLabel);
        phone.setPreferredSize(new Dimension(200,30));
        jPanel.add(phone);

        addressLabel.setPreferredSize(new Dimension(80,30));
        jPanel.add(addressLabel);
        address.setPreferredSize(new Dimension(200,30));
        jPanel.add(address);

        jPanel.add(delUserBtn);

        Container contentPane = getContentPane();
        contentPane.add(jPanel);

        //添加监视器
        delUserListener.setDelUserView(this);
        personID.addItemListener(delUserListener);
        personID.addActionListener(delUserListener);
        delUserBtn.addActionListener(delUserListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

