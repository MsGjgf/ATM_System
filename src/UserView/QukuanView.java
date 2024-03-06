package UserView;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QukuanView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel qukuanLabel = new JLabel("取款金额:",JLabel.RIGHT);
    JTextField qukuanField = new JTextField();
    JButton qukuanBtn = new JButton("确认取款");
    QukuanViewListener qukuanViewListener = new QukuanViewListener();
    String cardID;
    public QukuanView(UserView userView){
        super(userView,"取款界面",true);

        //获取主界面的卡号
        cardID = userView.centerLabel.getText().toString().trim().substring(6,18);

        Container contentPane = getContentPane();

        //添加内容组件
        qukuanLabel.setFont(new Font("楷体",Font.PLAIN,20));
        jPanel.add(qukuanLabel);
        qukuanField.setFont(new Font("楷体",Font.PLAIN,20));
        qukuanField.setPreferredSize(new Dimension(100,30));
        jPanel.add(qukuanField);
        qukuanBtn.setFont(new Font("楷体",Font.PLAIN,20));
        jPanel.add(qukuanBtn);

        //添加面板
        contentPane.add(jPanel);

        //添加监听器
        qukuanViewListener.setQukuanView(this);
        qukuanBtn.addActionListener(qukuanViewListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

class QukuanViewListener implements ActionListener{
    private QukuanView qukuanView;

    public void setQukuanView(QukuanView qukuanView) {
        this.qukuanView = qukuanView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== qukuanView.qukuanBtn){
            String moneyStr = qukuanView.qukuanField.getText();
            //正则检查余额是否合法
            if (!moneyStr.matches("^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)")||Float.parseFloat(moneyStr)==0){
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
                String strUpdate = "update cardinfo set balance = balance - "+money+" where cardID = '"+qukuanView.cardID+"'";
                String strInsert = "insert into tradeinfo values(now(),'"+qukuanView.cardID+"','支出',"+money+")";
                String strSelect = "select balance from cardinfo where cardID = '"+qukuanView.cardID+"'";
                sql.executeUpdate(strUpdate);
                sql.executeUpdate(strInsert);
                rs = sql.executeQuery(strSelect);
                rs.next();
                balance = rs.getFloat("balance");
                /**开始事务**/
                con.commit();
                con.setAutoCommit(true);
                JOptionPane.showMessageDialog(null,"取款成功，余额："+balance);
                con.close();
                qukuanView.dispose();
            }
            catch (SQLException ex){
                try {
                    JOptionPane.showMessageDialog(null,"取款失败，余额不足！");
                    /**回滚**/
                    con.rollback();
                }
                catch (SQLException exp){
                }
            }
        }
    }
}
