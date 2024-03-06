package AdminView.UserInfo;

import AdminView.AdminView;
import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdUserView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel personIDLabel = new JLabel("身份证号",JLabel.RIGHT);
    JComboBox<String> personID = new JComboBox<String>();
    JLabel nameLabel = new JLabel("姓名",JLabel.RIGHT);
    JTextField name = new JTextField();
    JLabel phoneLabel = new JLabel("电话号码",JLabel.RIGHT);
    JTextField phone = new JTextField();
    JLabel addressLabel = new JLabel("家庭住址",JLabel.RIGHT);
    JTextField address = new JTextField();
    JButton updUserBtn = new JButton("修改客户信息");
    UpdUserListener updUserListener = new UpdUserListener();
    public UpdUserView(AdminView adminView){
        super(adminView,"修改客户信息界面",true);

        Connection con = null;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank","root","123456");
        if (con==null)
            return;
        try {
            sql = con.createStatement();
            rs = sql.executeQuery("select personID from userinfo");
            for (int i = 0;rs.next();i++){
                personID.addItem(rs.getString("personID"));
            }
            personID.setSelectedIndex(-1);
            con.close();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

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

        jPanel.add(updUserBtn);

        Container contentPane = getContentPane();
        contentPane.add(jPanel);


        //添加监视器
        updUserListener.setUpdUserView(this);
        personID.addItemListener(updUserListener);
        personID.addActionListener(updUserListener);
        updUserBtn.addActionListener(updUserListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}