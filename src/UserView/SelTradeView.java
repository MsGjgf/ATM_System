package UserView;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class SelTradeView extends JDialog {
    public SelTradeView(UserView userView){
        super(userView,"查询账单",true);

        Container contentPane = getContentPane();

        Connection con = null;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank","root","123456");
        if (con==null)
            return;
        try {
            sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = sql.executeQuery("select * from tradeinfo where cardID = '"+userView.centerLabel.getText().toString().trim().substring(6,18)+"'");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();            //字段数目
            String[] columnName = new String[columnCount];
            for (int i = 1; i <= columnCount; i++){
                columnName[i-1]=metaData.getColumnName(i);
                System.out.println(columnName[i-1]);
            }
            rs.last();
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
            JTable table = new JTable();
            table.setModel(new SelTradeTableMode(record,columnName));
            Render render = new Render();
            table.setDefaultRenderer(Object.class,render);
            JTableHeader jTableHeader = table.getTableHeader();
            jTableHeader.setFont(new Font(null, Font.BOLD,16));
            table.setFont(new Font(null,Font.PLAIN,14));
            table.setRowHeight(30);

            contentPane.add(new JScrollPane(table));
            setSize(600,400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setResizable(false);
            setVisible(true);
        }
        catch (SQLException e){
        }
    }
}

//设置表格不可编辑
class SelTradeTableMode extends DefaultTableModel {
    public SelTradeTableMode(Object[][] tableData, Object[] colNames){
        super (tableData,colNames);
    }
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

//设置表格隔行颜色
class Render extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (row%2==0){
            setBackground(Color.LIGHT_GRAY);
        }else {
            setBackground(Color.WHITE);
        }
        setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}