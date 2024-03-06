package AdminView.Deposit;

import AdminView.AdminView;
import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdDepositView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel updDepositLabel = new JLabel("存款类型：",JLabel.RIGHT);
    JComboBox<String> updDepositOle = new JComboBox<String>();
    JTextField updDepositNew = new JTextField(10);
    JButton updDepositBtn = new JButton("修改类型");
    UpdDepositListener updDepositListener = new UpdDepositListener();
    public UpdDepositView(AdminView adminView){
        super(adminView,"添加存款类型界面",true);

        Container contentPane = getContentPane();
        jPanel.add(updDepositLabel);
        jPanel.add(updDepositOle);
        jPanel.add(updDepositNew);
        jPanel.add(updDepositBtn);
        contentPane.add(jPanel);

        //连接数据库读取已存在的存款类型数据
        Connection con = null;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank","root","123456");
        if (con==null)
            return;
        try {
            sql = con.createStatement();
            rs = sql.executeQuery("select * from deposit");
            while (rs.next()){
                updDepositOle.addItem(rs.getString("savingName"));
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        updDepositListener = new UpdDepositListener();
        updDepositListener.setUpdDepositView(this);
        updDepositBtn.addActionListener(updDepositListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}