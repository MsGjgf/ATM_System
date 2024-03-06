package AdminView.Deposit;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class AddDepositListener implements ActionListener {
    private AddDepositView addDepositView;

    public void setAddDepositView(AddDepositView addDepositView) {
        this.addDepositView = addDepositView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDepositView.addDepositBtn) {
            String trade = addDepositView.addDeposit.getText().toString().trim();
            if (trade.equals("")) {
                JOptionPane.showMessageDialog(null, "不能为空！");
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
                sql.executeUpdate("insert into deposit values(null,'" + trade + "')");
                /**开始事务**/
                con.commit();
                con.setAutoCommit(true);
                JOptionPane.showMessageDialog(null, "添加成功！");
                con.close();
                addDepositView.dispose();
            } catch (SQLException ex) {
                try {
                    con.rollback();     /**若存在，则回滚**/
                    JOptionPane.showMessageDialog(null, "此存款类型已存在！");
                } catch (SQLException exp) {
                }
            }
        }
    }
}
