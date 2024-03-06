package AdminView.UserInfo;

import AdminView.AdminView;

import javax.swing.*;
import java.awt.*;

public class AddUserView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel personIDLabel = new JLabel("身份证号",JLabel.RIGHT);
    JTextField personID = new JTextField();
    JLabel nameLabel = new JLabel("姓名",JLabel.RIGHT);
    JTextField name = new JTextField();
    JLabel phoneLabel = new JLabel("电话号码",JLabel.RIGHT);
    JTextField phone = new JTextField();
    JLabel addressLabel = new JLabel("家庭住址",JLabel.RIGHT);
    JTextArea address = new JTextArea(5,5);
    JButton addUserBtn = new JButton("添加客户");
    AddUserListener addUserListener = new AddUserListener();
    public AddUserView(AdminView adminView) {
        super(adminView,"添加客户界面",true);

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

        jPanel.add(addUserBtn);

        //监听器
        addUserListener.setAddUserView(this);
        addUserBtn.addActionListener(addUserListener);

        Container contentPane = getContentPane();
        contentPane.add(jPanel);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

