package AdminView.Deposit;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class UpdDepositListener implements ActionListener {
    private UpdDepositView updDepositView;

    public void setUpdDepositView(UpdDepositView updDepositView) {
        this.updDepositView = updDepositView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updDepositView.updDepositBtn) {
            String dataOld = updDepositView.updDepositOle.getSelectedItem().toString();
            String dataNew = updDepositView.updDepositNew.getText().toString().trim();
            if (dataOld.equals(dataNew)) {
                JOptionPane.showMessageDialog(null, "不可与当前存储类型一致！");
                return;
            }

            Connection con = null;
            Statement sql;
            con = GetDBConnection.connectDB("bank", "root", "123456");
            if (con == null)
                return;
            try {
                con.setAutoCommit(false);
                sql = con.createStatement();
                sql.executeUpdate("update deposit set savingName ='" + dataNew + "' where savingName ='" + dataOld + "'");
                /**开始事务**/
                con.commit();
                con.setAutoCommit(true);
                JOptionPane.showMessageDialog(null, "修改成功！");
                con.close();
                updDepositView.dispose();
            } catch (SQLException ex) {
                try {
                    con.rollback();     /**回滚**/
                    JOptionPane.showMessageDialog(null, "修改失败，存储类型已存在!");
                } catch (SQLException exp) {
                }
            }
        }
    }
}
