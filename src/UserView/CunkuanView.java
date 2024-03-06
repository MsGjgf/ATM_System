package UserView;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CunkuanView extends JDialog {
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
    JLabel cunkuanLabel = new JLabel("存款金额:",JLabel.RIGHT);
    JTextField cunkuanField = new JTextField();
    JButton cunkuanBtn = new JButton("确认存款");
    CunkuanViewListener cunkuanViewListener = new CunkuanViewListener();
    String cardID;
    public CunkuanView(UserView userView){
        super(userView,"存款界面",true);

        //获取主界面的卡号
        cardID = userView.centerLabel.getText().toString().trim().substring(6,18);

        Container contentPane = getContentPane();

        //添加内容组件
        cunkuanLabel.setFont(new Font("楷体",Font.PLAIN,20));
        jPanel.add(cunkuanLabel);
        cunkuanField.setPreferredSize(new Dimension(100,30));
        cunkuanField.setFont(new Font("楷体",Font.PLAIN,20));
        jPanel.add(cunkuanField);
        cunkuanBtn.setFont(new Font("楷体",Font.PLAIN,20));
        jPanel.add(cunkuanBtn);

        //添加面板
        contentPane.add(jPanel);

        //添加监听器
        cunkuanViewListener.setCunkuanView(this);
        cunkuanBtn.addActionListener(cunkuanViewListener);

        setSize(350,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

class CunkuanViewListener implements ActionListener{
    private CunkuanView cunkuanView;

    public void setCunkuanView(CunkuanView cunkuanView) {
        this.cunkuanView = cunkuanView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== cunkuanView.cunkuanBtn){
            String moneyStr = cunkuanView.cunkuanField.getText();
            //正则检查金额是否合法
            if (!moneyStr.matches("^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)")){
                JOptionPane.showMessageDialog(null,"非法金额！");
                return;
            }
            float money = Float.parseFloat(moneyStr),balance=0.0f;
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
                String strUpdate = "update cardinfo set balance = balance + "+money+" where cardID = '"+cunkuanView.cardID+"'";
                String strInsert = "insert into tradeinfo values(now(),"+cunkuanView.cardID+",'存入',"+money+")";
                String strSelect = "select balance from cardinfo where cardID = '"+cunkuanView.cardID+"'";
                sql.executeUpdate(strUpdate);
                sql.executeUpdate(strInsert);
                rs = sql.executeQuery(strSelect);
                rs.next();
                balance = rs.getFloat("balance");
                /**开始事务**/
                con.commit();
                con.setAutoCommit(true);
                JOptionPane.showMessageDialog(null,"存款成功，余额："+balance);
                con.close();
                cunkuanView.dispose();
            }
            catch (SQLException ex){
                try {
                    JOptionPane.showMessageDialog(null,"金额异常，请重新输入");
                    cunkuanView.cunkuanField.setText("");
                    /**回滚**/
                    con.rollback();
                }
                catch (SQLException exp){
                }
            }
        }
    }
}
