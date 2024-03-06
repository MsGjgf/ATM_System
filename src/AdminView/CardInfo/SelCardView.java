package AdminView.CardInfo;

import AdminView.AdminView;
import GetDBConnection.GetDBConnection;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class SelCardView extends JDialog {
    public SelCardView(AdminView adminView){
        super(adminView,"查询卡号信息界面",true);

        Container contentPane = getContentPane();

        Connection con = null;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank","root","123456");
        if (con==null)
            return;
        try {
            sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            /**如果文本框为空则查询所有卡号，否则查询输入的卡号**/
            if (adminView.cardText.getText().toString().trim().equals("")){
                rs = sql.executeQuery("select * from cardinfo");
            }else {
                rs = sql.executeQuery("select * from cardinfo where cardID = '"+adminView.cardText.getText().toString().trim()+"'");
            }
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();            //字段数目
            String[] columnName = new String[columnCount];
            for (int i = 1; i <= columnCount; i++){
                columnName[i-1]=metaData.getColumnName(i);
                System.out.println(columnName[i-1]);
            }
            rs.last();
            rs.last();
            if (rs.getRow()<=0){
                JOptionPane.showMessageDialog(null,"卡号不存在！");
                return;
            }
            int recordAmount = rs.getRow();                        //结果集中的记录数目
            String[][] record = new String[recordAmount][columnCount];
            int i = 0;
            rs.beforeFirst();
            while (rs.next()){
                for (int j = 1; j <= columnCount; j++){
                    record[i][j-1] = rs.getString(j);
                }
                i++;
            }
            con.close();
            for (i = 0; i < recordAmount;i++){
                for (int j = 0; j < columnCount;j++){
                    System.out.print(record[i][j]+"\t");
                }
                System.out.print("\n");
            }
            //对表格的一些颜色、字体属性设置
            JTable table = new JTable();
            table.setModel(new SelCardTableMode(record,columnName));
            SelCardRender render = new SelCardRender();
            table.setDefaultRenderer(Object.class,render);
            JTableHeader jTableHeader = table.getTableHeader();
            jTableHeader.setFont(new Font(null, Font.BOLD,20));
            table.setFont(new Font(null,Font.PLAIN,14));
            table.setRowHeight(30);

            contentPane.add(new JScrollPane(table));
            setSize(1000,500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setResizable(false);
            setVisible(true);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
}

