package AdminView.UserInfo;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class DelUserListener implements ItemListener, ActionListener {
    private DelUserView delUserView;

    public void setDelUserView(DelUserView delUserView) {
        this.delUserView = delUserView;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Connection con;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank", "root", "123456");
        if (con == null)
            return;
        String personID;
        personID = delUserView.personID.getSelectedItem().toString();
        try {
            sql = con.createStatement();
            rs = sql.executeQuery("select * from userinfo where personID = '" + personID + "'");
            while (rs.next()) {
                delUserView.name.setText(rs.getString("customerName"));
                delUserView.phone.setText(rs.getString("telephone"));
                delUserView.address.setText(rs.getString("address"));
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delUserView.delUserBtn) {
            Connection con = null;
            Statement sql;
            con = GetDBConnection.connectDB("bank", "root", "123456");
            if (con == null)
                return;
            try {
                con.setAutoCommit(false);       /**先关闭自动提交模式**/
                sql = con.createStatement();
                sql.executeUpdate("delete from userinfo where personID = '" + delUserView.personID.getSelectedItem() + "'");

                con.commit();
                JOptionPane.showMessageDialog(null, "删除客户信息成功！");
                con.setAutoCommit(true);
                con.close();
                delUserView.dispose();
            } catch (SQLException ex) {
                try {
                    con.rollback();
                    JOptionPane.showMessageDialog(null, "删除失败，该客户在银行有账户！！！", "警告！", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException exp) {}
            }
        }
    }
}
