package AdminView.CardInfo;

import javax.swing.table.DefaultTableModel;

//设置表格不可编辑
class SelCardTableMode extends DefaultTableModel {
    public SelCardTableMode(Object[][] tableData, Object[] colNames) {
        super(tableData, colNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
