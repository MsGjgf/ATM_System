package UserView;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SelBalanceView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel balanceLabel = new JLabel("余额：");
    public SelBalanceView(UserView userView){
        super(userView,"查询余额界面",true);

        balanceLabel.setFont(new Font("楷体",Font.PLAIN,30));
        jPanel.add(balanceLabel);

        Container contentPane = getContentPane();
        contentPane.add(jPanel);

        /**连接数据库**/
        Connection con = null;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank","root","123456");
        if (con==null)
            return;
        try {
            sql = con.createStatement();
            String strSelect = "select balance from cardinfo where cardID = '"+userView.centerLabel.getText().toString().trim().substring(6,18)+"'";
            System.out.println(strSelect);
            rs = sql.executeQuery(strSelect);
            rs.next();
            float balance = rs.getFloat("balance");
            balanceLabel.setText("余额："+balance);
        }
        catch (SQLException ex){}

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}