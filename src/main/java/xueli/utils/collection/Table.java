package xueli.utils.collection;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Table<T> extends HashMap<Integer, HashMap<Integer, T>> {

	private static final long serialVersionUID = 3543645039023993865L;

	private int maxX = 0, maxY = 0;

	public void put(int x, int y, T value) {
		if (!super.containsKey(x))
			super.put(x, new HashMap<Integer, T>());

		maxX = Math.max(x, maxX);
		maxY = Math.max(y, maxY);

		super.get(x).put(y, value);
	}

	public T get(int x, int y) {
		HashMap<Integer, T> map = super.get(x);
		if (map == null)
			return null;
		return map.get(y);
	}

	public void saveToExcel(File f) throws IOException {
		Workbook b = new HSSFWorkbook();
		Sheet s = b.createSheet();

		for (int x = 0; x <= maxX; x++) {
			for (int y = 0; y <= maxY; y++) {
				setValue(y, x, get(x, y).toString(), s);
			}
		}

		FileOutputStream o = new FileOutputStream(f);
		b.write(o);
		o.flush();
		o.close();

	}

	private static void setValue(int row, int cell, String v, Sheet s) {
		Row r = s.getRow(row);
		if (r == null)
			r = s.createRow(row);
		Cell c = r.getCell(cell);
		if (c == null)
			c = r.createCell(cell);
		c.setCellValue(v);

	}

}
