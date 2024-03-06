package AdminView.CardInfo;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class AddCardListener implements ActionListener {
    private AddCardView addCardView;

    public void setAddCardView(AddCardView addCardView) {
        this.addCardView = addCardView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCardView.addCardBtn) {
            String savingName = addCardView.savingType.getSelectedItem().toString();
            String pwd = addCardView.pwd.getText();
            String personID = addCardView.personID.getSelectedItem().toString();

            Connection con = null;
            CallableStatement cs;
            con = GetDBConnection.connectDB("bank", "root", "123456");
            if (con == null)
                return;
            try {
                /**执行存储过程**/
                cs = con.prepareCall("{CALL cardid(?,?,?,?)}");
                cs.setString(1, savingName);
                cs.setString(2, pwd);
                cs.setString(3, personID);
                cs.registerOutParameter(4, Types.VARCHAR);
                cs.execute();
                /**获取存储过程out参数return回来的卡号**/
                String cardResult = cs.getString(4);
                JOptionPane.showMessageDialog(null, "开户成功！\n卡号:" + cardResult + "\n密码:" + pwd);
                con.close();
                addCardView.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "开户失败!仅限6位密码", null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
