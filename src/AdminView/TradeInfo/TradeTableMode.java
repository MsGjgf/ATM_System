package AdminView.TradeInfo;

import javax.swing.table.DefaultTableModel;

//表格不可编辑
class TradeTableMode extends DefaultTableModel {
    public TradeTableMode(Object[][] tableData, Object[] colNames) {
        super(tableData, colNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
