package AdminView.CardInfo;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class DelCardListener implements ItemListener, ActionListener {
    private DelCardView delCardView;

    public void setDelCardView(DelCardView delCardView) {
        this.delCardView = delCardView;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Connection con = null;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank", "root", "123456");
        if (con == null)
            return;
        String cardID = delCardView.cardID.getSelectedItem().toString();
        try {
            sql = con.createStatement();
            rs = sql.executeQuery("select * from cardinfo where cardID = '" + cardID + "'");
            while (rs.next()) {
                delCardView.status.setText(rs.getString("status"));
                delCardView.savingType.setText(rs.getString("savingName"));
                delCardView.balance.setText(String.valueOf(rs.getFloat("balance")));
                delCardView.pwd.setText(rs.getString("pwd"));
                delCardView.personID.setText(rs.getString("personID"));
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delCardView.delCardBtn) {
            Connection con = null;
            Statement sql;
            ResultSet rs;
            con = GetDBConnection.connectDB("bank", "root", "123456");
            if (con == null)
                return;
            try {
                con.setAutoCommit(false);       /**先关闭自动提交模式**/
                sql = con.createStatement();
                rs = sql.executeQuery("select balance from cardinfo where cardID = '" + delCardView.cardID.getSelectedItem() + "'");
                rs.next();
                //销户需要提示账户余额清空
                if (rs.getFloat("balance") > 0) {
                    JOptionPane.showMessageDialog(null, "请先清空账户！");
                    return;
                }
                int ok = sql.executeUpdate("delete from cardinfo where cardID = '" + delCardView.cardID.getSelectedItem() + "'");
                if (ok == 1) {
                    //二次确认销户
                    if (JOptionPane.showConfirmDialog(null, "是否确认删除该账户？", "确认对话框", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                        try {
                            con.rollback(); /**撤销事务所做的操作**/
                            JOptionPane.showMessageDialog(null, "取消成功，该账户未删除！");
                            return;
                        } catch (SQLException exp) {
                            System.out.println(exp);
                            System.out.println("事务撤销失败！");
                        }
                    }
                    con.commit();          /**开始事务处理**/
                    JOptionPane.showMessageDialog(null, "销户成功！");
                    delCardView.dispose();
                }
                con.setAutoCommit(true);            /**恢复自动提交模式**/
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "删除失败！");
            }
        }
    }
}
