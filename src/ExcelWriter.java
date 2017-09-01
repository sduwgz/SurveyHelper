import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class ExcelWriter {
	String fileName;
	Workbook wb;
	FileOutputStream fileOut;
	public ExcelWriter() throws FileNotFoundException{
		fileName = "output.xls";
		fileOut = new FileOutputStream(fileName);
	}
	public ExcelWriter(String s) throws FileNotFoundException{
		fileName = s;
		fileOut = new FileOutputStream(fileName, true);
	}
	public void newTable(String[] table) throws IOException{
		wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		Row row = sheet.createRow(0);
		for(int i = 0; i < table.length; ++ i){
			Cell cell = row.createCell(i);
			cell.setCellValue(table[i]);
		}
	    wb.write(fileOut);
	    fileOut.close();
	    wb.close();
	}
	public void writeRows(String[] record) throws IOException{
		 File file = new File(fileName);  
         try {  
             wb = new HSSFWorkbook(new FileInputStream(file));  
         } catch (FileNotFoundException e){
             e.printStackTrace();
         } catch (IOException e){
             e.printStackTrace();
         }
		Sheet sheet = wb.getSheetAt(0);
		System.out.println(sheet.getSheetName());
		if(sheet.getRow(0).getPhysicalNumberOfCells() != record.length)
			System.out.println("a bad record: " + record);
		else {
			System.out.println(sheet.getLastRowNum());
			Row row = sheet.createRow(sheet.getLastRowNum() + 1);
			for(int i = 0; i < record.length; ++ i){
				Cell cell = row.createCell(i);
				CellStyle cs = wb.createCellStyle();
			    
				cell.setCellValue(record[i]);
				System.out.println(cell.getStringCellValue());
				cs.setWrapText(true);
			    cell.setCellStyle(cs);
			}
		}
		file = new File(fileName);
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		wb.close();
		out.close();
	}
	public void closeStream() throws IOException{
		fileOut.close();
	}
	public static void main(String[] args) throws IOException{
		String[] s = {"a", "b", "c"};
		String[] ss = {"1", "2", "3"};
		ExcelWriter ew = new ExcelWriter();
		ew.newTable(s);
		ew.writeRows(ss);
		ew.writeRows(ss);
		ew.writeRows(ss);
		//ew.wb.write(ew.fileOut);
		ew.closeStream();
	}
}
