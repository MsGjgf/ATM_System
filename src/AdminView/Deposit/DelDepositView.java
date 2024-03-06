package AdminView.Deposit;

import AdminView.AdminView;
import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DelDepositView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel delDepositLabel = new JLabel("存款类型：",JLabel.RIGHT);
    JComboBox<String> delDeposit = new JComboBox<String>();
    JButton delDepositBtn = new JButton("删除");
    DelDepositListener delDepositListener = new DelDepositListener();
    public DelDepositView(AdminView adminView){
        super(adminView,"删除存款类型界面",true);

        Container contentPane = getContentPane();
        jPanel.add(delDepositLabel);
        jPanel.add(delDeposit);
        jPanel.add(delDepositBtn);
        contentPane.add(jPanel);

        //获取数据库中的所有存款类型
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
                delDeposit.addItem(rs.getString("savingName"));
            }
            con.close();
        }
        catch (SQLException ex){
            System.out.println(ex);
        }

        //监听器
        delDepositListener = new DelDepositListener();
        delDepositListener.setDelDepositView(this);
        delDepositBtn.addActionListener(delDepositListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

