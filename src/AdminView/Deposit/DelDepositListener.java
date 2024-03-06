package AdminView.Deposit;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class DelDepositListener implements ActionListener {
    private DelDepositView delDepositView;

    public void setDelDepositView(DelDepositView delDepositView) {
        this.delDepositView = delDepositView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delDepositView.delDepositBtn) {
            Connection con = null;
            Statement sql;
            con = GetDBConnection.connectDB("bank", "root", "123456");
            if (con == null)
                return;
            try {
                con.setAutoCommit(false);
                sql = con.createStatement();
                sql.executeUpdate("delete from deposit where savingName ='" + delDepositView.delDeposit.getSelectedItem().toString().trim() + "'");
                /**开始事务**/
                con.commit();
                con.setAutoCommit(true);
                JOptionPane.showMessageDialog(null, "删除成功！");
                con.close();
                delDepositView.dispose();
            } catch (SQLException ex) {
                try {
                    con.rollback();     /**若还有银行卡正在使用该类型，则回滚**/
                    JOptionPane.showMessageDialog(null, "删除失败，该类型正在使用!");
                } catch (SQLException exp) {
                }
            }
        }
    }
}
