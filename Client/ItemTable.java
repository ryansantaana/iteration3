
import javax.swing.table.DefaultTableModel;

public class ItemTable extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemTable() {
		// TODO Auto-generated constructor stub
	}

	public ItemTable(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		// TODO Auto-generated constructor stub
	}

	public ItemTable(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public ItemTable(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void clearTable() {
		for (int i = getRowCount() - 1; i > -1; i--) {
			removeRow(i);
		}
	}
}
