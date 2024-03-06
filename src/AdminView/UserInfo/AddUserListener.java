package AdminView.UserInfo;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class AddUserListener implements ActionListener {
    private AddUserView addUserView;

    public void setAddUserView(AddUserView addUserView) {
        this.addUserView = addUserView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addUserView.addUserBtn) {
            Connection con = null;
            Statement sql;
            con = GetDBConnection.connectDB("bank", "root", "123456");
            if (con == null)
                return;
            try {
                con.setAutoCommit(false);
                sql = con.createStatement();
                sql.executeUpdate("insert into userinfo values('" + addUserView.personID.getText().toString().trim()
                        + "','" + addUserView.name.getText().toString().trim()
                        + "','" + addUserView.phone.getText().toString().trim()
                        + "','" + addUserView.address.getText().toString().trim() + "')");
                /**开始事务**/
                con.commit();
                JOptionPane.showMessageDialog(null, "客户添加成功！");
                con.setAutoCommit(true);
                con.close();
                addUserView.dispose();
            } catch (SQLException ex) {
                try {
                    con.rollback();     /**回滚**/
                    JOptionPane.showMessageDialog(null, "信息有误或客户身份证重复，请重新输入！");
                } catch (SQLException exp) {
                }
            }
        }
    }
}
