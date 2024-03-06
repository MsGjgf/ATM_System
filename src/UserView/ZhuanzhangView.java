package UserView;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ZhuanzhangView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel zhuanzhangLabel = new JLabel("转账金额:",JLabel.RIGHT);
    JTextField zhuanzhangField = new JTextField(18);
    JLabel cardIDLabel = new JLabel("银行卡号:",JLabel.RIGHT);
    JTextField cardIDField = new JTextField(18);
    JButton zhuanzhangBtn = new JButton("确认转账");
    ZhuanzhangViewListener zhuanzhangViewListener = new ZhuanzhangViewListener();
    String cardID;
    public ZhuanzhangView(UserView userView){
        super(userView,"转账界面",true);

        //获取主界面的卡号
        cardID = userView.centerLabel.getText().toString().trim().substring(6,18);

        Container contentPane = getContentPane();

        //添加内容组件
        zhuanzhangLabel.setPreferredSize(new Dimension(100,30));
        jPanel.add(zhuanzhangLabel);
        zhuanzhangField.setPreferredSize(new Dimension(100,30));
        jPanel.add(zhuanzhangField);
        cardIDLabel.setPreferredSize(new Dimension(100,30));
        jPanel.add(cardIDLabel);
        cardIDField.setPreferredSize(new Dimension(100,30));
        jPanel.add(cardIDField);
        jPanel.add(zhuanzhangBtn);

        //添加面板
        contentPane.add(jPanel);

        //添加监听器
        zhuanzhangViewListener.setZhuanzhangView(this);
        zhuanzhangBtn.addActionListener(zhuanzhangViewListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

class ZhuanzhangViewListener implements ActionListener{
    private ZhuanzhangView zhuanzhangView;

    public void setZhuanzhangView(ZhuanzhangView zhuanzhangView) {
        this.zhuanzhangView = zhuanzhangView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== zhuanzhangView.zhuanzhangBtn){
            //获取当前卡号
            String nowCardID = zhuanzhangView.cardID;
            //获取目标卡号
            String targetCardID = zhuanzhangView.cardIDField.getText().toString().trim();
            //判断是否为目标账户是否是自己
            if (nowCardID.equals(targetCardID)){
                JOptionPane.showMessageDialog(null,"卡号不能为当前卡号！");
                return;
            }
            //获取转账金额
            String moneyStr = zhuanzhangView.zhuanzhangField.getText();
            if (targetCardID.equals("")||moneyStr.equals("")){
                JOptionPane.showMessageDialog(null,"不能为空！");
                return;
            }
            if (!moneyStr.matches("^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)")){
                JOptionPane.showMessageDialog(null,"非法金额！");
                return;
            }
            float money = Float.parseFloat(moneyStr),balance = 0.0f;
            if (money>100000){
                JOptionPane.showMessageDialog(null,"超额");
                return;
            }
            Connection con = null;
            Statement sql;
            ResultSet rs;
            con = GetDBConnection.connectDB("bank","root","123456");
            if (con==null)
                return;
            try {
                con.setAutoCommit(false);
                sql = con.createStatement();
                //出账
                String strUpdateNow = "update cardinfo set balance = balance - "+money+" where cardID = '"+nowCardID+"'";
                //入账
                String strUpdateTarget = "update cardinfo set balance = balance + "+money+" where cardID = '"+targetCardID+"'";
                //当前卡号交易记录
                String strInsertNow = "insert into tradeinfo values(now(),'"+nowCardID+"','支出',"+money+")";
                //目标卡号交易记录
                String strInsertTarget = "insert into tradeinfo values(now(),'"+targetCardID+"','存入',"+money+")";
                //查询当前卡号还剩余额
                String strSelect = "select balance from cardinfo where cardID = '"+nowCardID+"'";
                sql.executeUpdate(strUpdateNow);
                sql.executeUpdate(strUpdateTarget);
                sql.executeUpdate(strInsertNow);
                sql.executeUpdate(strInsertTarget);
                rs = sql.executeQuery(strSelect);
                rs.next();
                balance = rs.getFloat("balance");
                /**开始事务**/
                con.commit();
                con.setAutoCommit(true);
                JOptionPane.showMessageDialog(null,"转账成功！余额："+balance);
                con.close();
                zhuanzhangView.dispose();
            }
            catch (SQLException ex){
                try {
                    JOptionPane.showMessageDialog(null,"转账失败，卡号错误或余额不足！");
                    /**回滚**/
                    con.rollback();
                }
                catch (SQLException exp){}
            }
        }
    }
}
