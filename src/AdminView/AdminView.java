package AdminView;

import GetDBConnection.GetDBConnection;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class AdminView extends JFrame {
    public JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    public JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    public JLabel userLabel = new JLabel("客户信息：");
    public JButton addUserBtn = new JButton("添加客户信息");
    public JButton updUserBtn = new JButton("修改客户信息");
    public JButton delUserBtn = new JButton("删除客户信息");
    public JTextField userText = new JTextField(18);
    public JButton selUserBtn = new JButton("查询客户");
    public JLabel cardLabel = new JLabel("银行卡信息：");
    public JButton addCardBtn = new JButton("开户");
    public JButton updCardBtn = new JButton("修改银行卡信息");
    public JButton delCardBtn = new JButton("销户");
    public JTextField cardText = new JTextField(18);
    public JLabel depositLabel = new JLabel("交易类型：");
    public JButton selCardBtn = new JButton("查询卡号信息");
    public JButton selTradeBtn = new JButton("查询交易信息");
    public JButton addDepositBtn = new JButton("添加交易类型");
    public JButton updDepositBtn = new JButton("修改交易类型");
    public JButton delDepositBtn = new JButton("删除交易类型");
    public JButton exitBtn = new JButton("注销登录");
    AdminViewTable adminViewTable = new AdminViewTable();
    AdminListener mainListener = new AdminListener();

    public AdminView() throws InterruptedException {
        super("ATM存取款系统-管理员主界面");
        Container contentPane = getContentPane();

        //放置北边的组件
        userLabel.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(userLabel);
        addUserBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(addUserBtn);
        updUserBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(updUserBtn);
        delUserBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(delUserBtn);
        userText.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(userText);
        selUserBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(selUserBtn);
        cardLabel.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(cardLabel);
        addCardBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(addCardBtn);
        updCardBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(updCardBtn);
        delCardBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(delCardBtn);
        cardText.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(cardText);
        selCardBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        northPanel.add(selCardBtn);

        //放置南边的组件
        selTradeBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        southPanel.add(selTradeBtn);
        depositLabel.setFont(new Font("楷体", Font.PLAIN, 18));
        southPanel.add(depositLabel);
        addDepositBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        southPanel.add(addDepositBtn);
        updDepositBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        southPanel.add(updDepositBtn);
        delDepositBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        southPanel.add(delDepositBtn);
        exitBtn.setFont(new Font("楷体", Font.PLAIN, 18));
        southPanel.add(exitBtn);

        contentPane.add(southPanel, BorderLayout.SOUTH);
        contentPane.add(northPanel, BorderLayout.NORTH);

        String strSql = "select * from cardinfo";
        cope(contentPane, strSql);

        //添加监听器
        mainListener.setView(this);
        addUserBtn.addActionListener(mainListener);
        updUserBtn.addActionListener(mainListener);
        delUserBtn.addActionListener(mainListener);
        selUserBtn.addActionListener(mainListener);

        addCardBtn.addActionListener(mainListener);
        updCardBtn.addActionListener(mainListener);
        selCardBtn.addActionListener(mainListener);
        delCardBtn.addActionListener(mainListener);

        selTradeBtn.addActionListener(mainListener);

        addDepositBtn.addActionListener(mainListener);
        updDepositBtn.addActionListener(mainListener);
        delDepositBtn.addActionListener(mainListener);

        exitBtn.addActionListener(mainListener);

        //根据屏幕大小设置主界面大小
        setBounds(DimensionUtil.getBounds());
        //设置窗体完全充满整个屏幕的可见大小
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    public void cope(Container contentPane, String strSql) throws InterruptedException {
        /**初始化表格数据**/
        Vector<Vector<Object>> data = new Vector<>();
        Connection con;
        Statement sql;
        ResultSet rs;
        con = GetDBConnection.connectDB("bank", "root", "123456");
        if (con == null)
            return;
        try {
            sql = con.createStatement();
            rs = sql.executeQuery(strSql);
            while (rs.next()) {
                Vector<Object> rowVector = new Vector<>();

                String cardID = rs.getString(1);
                int status = rs.getInt(2);
                String savingName = rs.getString(3);
                Date date = rs.getDate(4);
                float balance = rs.getFloat(5);
                String pwd = rs.getString(6);
                String personID = rs.getString(7);
                rowVector.addElement(cardID);
                rowVector.addElement(status);
                rowVector.addElement(savingName);
                rowVector.addElement(date);
                rowVector.addElement(balance);
                rowVector.addElement(pwd);
                rowVector.addElement(personID);

                data.addElement(rowVector);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        AdminViewTableModel adminViewTableModel = AdminViewTableModel.assembleModel(data);
        adminViewTable.setModel(adminViewTableModel);
        adminViewTable.renderRule();
        JScrollPane jScrollPane = new JScrollPane(adminViewTable);
        contentPane.add(jScrollPane, BorderLayout.CENTER);
        validate();
    }
}

//设置隔行颜色
class AdminViewCellRender extends DefaultTableCellRenderer {
    //在每一行的每一列之前都会调用
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (row % 2 == 0) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(Color.WHITE);
        }
        setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}

class AdminViewTable extends JTable{
    public AdminViewTable(){
        //设置表头
        JTableHeader jTableHeader = getTableHeader();
        jTableHeader.setFont(new Font(null,Font.BOLD,16));
        jTableHeader.setForeground(Color.red);
        //设置表格体
        setFont(new Font(null,Font.PLAIN,14));
        setForeground(Color.BLACK);
        setGridColor(Color.BLACK);
        setRowHeight(30);
    }
    public void renderRule(){
        //设置表格列的渲染方式
        Vector<String> columns = AdminViewTableModel.getColumns();
        AdminViewCellRender render = new AdminViewCellRender();
        for (int i = 0; i < columns.size();i++){
            TableColumn column = getColumn(columns.get(i));
            column.setCellRenderer(render);
        }
    }
}

class AdminViewTableModel extends DefaultTableModel {
    static Vector<String> columns = new Vector<>();
    static {
        columns.addElement("卡号");
        columns.addElement("卡状态");
        columns.addElement("存款类型");
        columns.addElement("开卡时间");
        columns.addElement("余额");
        columns.addElement("密码");
        columns.addElement("身份证号");
    }
    private AdminViewTableModel(){
        super(null,columns);
    }
    private static AdminViewTableModel adminViewTableModel = new AdminViewTableModel();
    public static AdminViewTableModel assembleModel(Vector<Vector<Object>> data){
        adminViewTableModel.setDataVector(data,columns);
        return adminViewTableModel;
    }

    public static Vector<String> getColumns() {
        return columns;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

class DimensionUtil{
    public static Rectangle getBounds(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //保证主界面不会覆盖电脑屏幕的任务栏
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(new JFrame().getGraphicsConfiguration());
        Rectangle rectangle = new Rectangle(screenInsets.left,screenInsets.top,
                screenSize.width-screenInsets.left-screenInsets.right,
                screenSize.height-screenInsets.top-screenInsets.bottom);
        return rectangle;
    }
}