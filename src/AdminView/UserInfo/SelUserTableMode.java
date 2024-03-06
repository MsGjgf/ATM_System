package AdminView.UserInfo;

import javax.swing.table.DefaultTableModel;

//设置表格不可编辑
class SelUserTableMode extends DefaultTableModel {
    public SelUserTableMode(Object[][] tableData, Object[] colNames) {
        super(tableData, colNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
