package LoginView;

import GetDBConnection.GetDBConnection;
import AdminView.AdminView;
import UserView.UserView;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginListener extends KeyAdapter implements ActionListener {
    private LoginView loginView;
    public LoginListener(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (loginView.optionText.getSelectedItem().toString().equals("客户")){
            if (e.getSource()==loginView.loginBtn){
                try {
                    login();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource()==loginView.resetBtn) {
                loginView.cardFiled.setText("");
                loginView.pwdFiled.setText("");
            }
        } else if (loginView.optionText.getSelectedItem().toString().equals("管理员")) {
            if (e.getSource()==loginView.loginBtn){
                try {
                    admin();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource()==loginView.resetBtn) {
                loginView.cardFiled.setText("");
                loginView.pwdFiled.setText("");
            }
        }
    }

    private void login() throws InterruptedException {
        //获取卡号密码
        String cardID = loginView.cardFiled.getText().toString().trim();
        char[] cardPasswordArray = loginView.pwdFiled.getPassword();
        String cardPwd = new String(cardPasswordArray);
        //判空
        if (cardID.equals("")||cardPwd.equals("")){
            JOptionPane.showMessageDialog(null,"账号密码不能为空！");
            return;
        }
        boolean flag = false;
        /**查询db**/
        Connection con = GetDBConnection.connectDB("bank","root","123456");
        if (con==null)
            return;
        String strSql = "select * from cardinfo where cardID = '"+cardID+"' and pwd = '"+cardPwd+"'";
        try {
            Statement sql = con.createStatement();
            ResultSet rs = sql.executeQuery(strSql);
            if (rs.next()){
                if (rs.getInt("status")==0){
                    JOptionPane.showMessageDialog(null,"该账户已冻结,请联系管理员!",null,JOptionPane.WARNING_MESSAGE);
                    return;
                }
                flag = true;
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        if (flag){
            //跳转到用户主界面并销毁登录界面
            new UserView(cardID);
            loginView.dispose();
        }else {
            JOptionPane.showMessageDialog(loginView,"卡号密码错误！");
        }
    }

    public void admin() throws InterruptedException {
        String admin = loginView.cardFiled.getText().toString().trim();
        char[] adminPasswordArray = loginView.pwdFiled.getPassword();
        String adminPwd = new String(adminPasswordArray);
        //判空
        if (admin.equals("")||adminPwd.equals("")){
            JOptionPane.showMessageDialog(null,"账号密码不能为空！");
            return;
        }
        boolean flag = false;
        /**查询db**/
        Connection con = GetDBConnection.connectDB("bank","root","123456");
        if (con==null)
            return;
        String strSql = "select * from admininfo where admin = '"+admin+"' and adminpwd = '"+adminPwd+"'";
        try {
            Statement sql = con.createStatement();
            ResultSet rs = sql.executeQuery(strSql);
            if (rs.next()){
                flag = true;
            }
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        if (flag){
            //跳转到管理员主界面并销毁登录界面
            new AdminView();
            loginView.dispose();
        }else {
            JOptionPane.showMessageDialog(loginView,"账号密码错误！");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_ENTER == e.getKeyCode()){
            try {
                login();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
