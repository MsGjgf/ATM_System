package AdminView.UserInfo;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class UpdUserListener implements ItemListener, ActionListener {
    private UpdUserView updUserView;

    public void setUpdUserView(UpdUserView updUserView) {
        this.updUserView = updUserView;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Connection con = null;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank", "root", "123456");
        if (con == null)
            return;
        String personID = updUserView.personID.getSelectedItem().toString();
        try {
            sql = con.createStatement();
            rs = sql.executeQuery("select * from userinfo where personID = '" + personID + "'");
            while (rs.next()) {
                updUserView.name.setText(rs.getString("customerName"));
                updUserView.phone.setText(rs.getString("telephone"));
                updUserView.address.setText(rs.getString("address"));
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updUserView.updUserBtn) {
            Connection con = null;
            Statement sql;
            ResultSet rs;
            con = GetDBConnection.connectDB("bank", "root", "123456");
            if (con == null)
                return;
            try {
                con.setAutoCommit(false);
                sql = con.createStatement();
                sql.executeUpdate("update userinfo set customerName = '" + updUserView.name.getText().toString().trim()
                        + "',telephone = '" + updUserView.phone.getText().toString().trim()
                        + "',address = '" + updUserView.address.getText().toString().trim()
                        + "' where personID = '" + updUserView.personID.getSelectedItem() + "'");
                /**开始事务**/
                con.commit();
                con.setAutoCommit(true);
                JOptionPane.showMessageDialog(null, "修改成功！");
                con.close();
                updUserView.dispose();
            } catch (SQLException ex) {
                try {
                    con.rollback();     /**回滚**/
                    JOptionPane.showMessageDialog(null, "信息有误！");
                } catch (SQLException exp) {
                }
            }
        }
    }
}
