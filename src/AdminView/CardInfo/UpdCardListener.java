package AdminView.CardInfo;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class UpdCardListener implements ItemListener, ActionListener {
    private UpdCardView updCardView;

    public void setUpdCardView(UpdCardView updCardView) {
        this.updCardView = updCardView;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Connection con = null;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank", "root", "123456");
        if (con == null)
            return;
        String cardID = updCardView.cardID.getSelectedItem().toString();
        try {
            sql = con.createStatement();
            rs = sql.executeQuery("select status,savingName,balance,pwd from cardinfo where cardID = '" + cardID + "'");
            while (rs.next()) {
                //与数据库提取到的卡号状态来设置卡号状态
                if (rs.getString("status").equals("1")) {
                    updCardView.status.setSelectedIndex(0);
                } else {
                    updCardView.status.setSelectedIndex(1);
                }
                for (int i = 0; i < updCardView.savingType.getItemCount(); i++) {
                    if (updCardView.savingType.getItemAt(i).equals(rs.getString("savingName"))) {
                        updCardView.savingType.setSelectedIndex(i);
                        break;
                    }
                }
                updCardView.balance.setText(rs.getString("balance"));
                updCardView.pwd.setText(rs.getString("pwd"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "修改失败！");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updCardView.updCardBtn) {
            //判断数据是否为空！
            if (updCardView.balance.getText().equals("") || updCardView.pwd.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "数据不能为空!");
                return;
            }

            String cardID = updCardView.cardID.getSelectedItem().toString();
            int status = Integer.parseInt(updCardView.status.getSelectedItem().toString());
            String savingName = updCardView.savingType.getSelectedItem().toString();

            //根据正则表达式来约束输入的金额是否合法
            if (!updCardView.balance.getText().toString().trim().matches("^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)")) {
                JOptionPane.showMessageDialog(null, "非法金额！");
                return;
            }

            float balance = Float.parseFloat(updCardView.balance.getText().toString());
            String pwd = updCardView.pwd.getText();

            Connection con = null;
            Statement sql;
            con = GetDBConnection.connectDB("bank", "root", "123456");
            if (con == null)
                return;
            try {
                con.setAutoCommit(false);       /**先关闭自动提交模式**/
                String str = "update cardinfo set status = " + status + ",savingName='" + savingName + "',balance=" + balance + ",pwd='" + pwd + "' where cardID='" + cardID + "'";
                sql = con.createStatement();    //再返回Statement对象
                int ok = sql.executeUpdate(str);
                if (ok == 1) {
                    //修改客户卡号信息比较严谨,所以加多一步确认弹框
                    if (JOptionPane.showConfirmDialog(null, "是否确认修改！", "确认对话框", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                        try {
                            con.rollback(); /**撤销事务所做的操作**/
                            JOptionPane.showMessageDialog(null, "取消成功，数据未作修改！");
                            return;
                        } catch (SQLException exp) {
                            System.out.println(exp);
                            System.out.println("事务撤销失败！");
                        }
                    }
                    con.commit();               /**开始事务处理**/
                    JOptionPane.showMessageDialog(null, "修改成功！");
                    updCardView.dispose();
                }
                con.setAutoCommit(true);       /**恢复自动提交模式**/
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "密码为6位数字！");
            }
        } else if (e.getSource() == updCardView.rootBtn) {
            if (updCardView.rootBtn.getText().equals("开启root")) {

                Connection con = null;
                Statement sql;
                ResultSet rs;
                con = GetDBConnection.connectDB("bank", "root", "123456");
                if (con == null) {
                    return;
                }
                String rootPwd =JOptionPane.showInputDialog(null,"请输入管理员密码","root",JOptionPane.PLAIN_MESSAGE);
                try {
                    sql = con.createStatement();
                    rs = sql.executeQuery("select adminpwd from admininfo");
                    rs.next();
                    if (!rs.getString("adminPwd").equals(rootPwd)){
                        JOptionPane.showMessageDialog(null,"密码错误！",null,JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    con.close();
                    updCardView.rootBtn.setText("关闭root");
                    updCardView.balance.setEnabled(true);       /**开启余额编辑**/
                }
                catch (SQLException exp) {
                    System.out.println(exp);
                }
            } else {
                updCardView.rootBtn.setText("开启root");
                updCardView.balance.setEnabled(false);          /**关闭余额编辑**/
            }
        }
    }
}
