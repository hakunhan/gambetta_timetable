package Model.ExcelTable;

import java.io.*;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Java program that write data from Excel file.
 */
public class EmployeeWorkTimeExcelPrinter {
    private Object[][] date  = new Object[10][8];
    private int currentDateIndex = 0;

    private Object[][] memTime;
    private int currentMemTimeIndex = 0;

    private GetTime time = new GetTime();
    private String month = time.getMonth();
    private int year = time.getYear();
    private File timetable = new File(month + "-" + year + ".xlsx");
    private int rowNum = 0;
    private int colorIndex = 0;


    /**
     *  constructor that create excel .xlsx file and set rows height and columns width
     */
    public EmployeeWorkTimeExcelPrinter(Object[][] memTime) throws IOException{
        this.memTime = memTime;
        GetTime time = new GetTime();
        String month = time.getMonth();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet 1");
        
        //setting columnWidth
        sheet.setColumnWidth(0, 5600);
        sheet.setColumnWidth(1, 5600);
        sheet.setColumnWidth(2, 5600);
        sheet.setColumnWidth(3, 5600);
        sheet.setColumnWidth(4, 5600);
        sheet.setColumnWidth(5, 5600);
        sheet.setColumnWidth(6, 5600);
        sheet.setColumnWidth(7, 5600);
        
        sheet.setDefaultRowHeight((short) 400);
        FileOutputStream outputStream = new FileOutputStream(month+ "-" + year + ".xlsx");
        workbook.write(outputStream);
        outputStream.close();
    }

    /**
     * add data to date
     */
    public void setDate(Object[] time) {
        date[currentDateIndex][0] = "TEAM";

        for(int i = 1; i <= 7; i++){
            date[currentDateIndex][i] = time[i-1];
        }

        currentDateIndex++;
    }

    /**
     * print date to excel file using date data
     */
    public void printDate() {
        try {
            FileInputStream file = new FileInputStream(timetable);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            Row row = sheet.createRow(rowNum++);
            Object[] key = date[currentDateIndex-1];
            
            int cellNum = 0;
            for (Object obj : key) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellStyle(FormatCell.format(workbook));
                if (obj instanceof Date)
                    cell.setCellValue((Date) obj);
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
                else if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
            }

            FileOutputStream outFile = new FileOutputStream(timetable);
            workbook.write(outFile);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * print member work time to excel file using memTime data
     */
    public void printMemTime() {
        try {
            FileInputStream file = new FileInputStream(timetable);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int counter = 0;

            for(Object[] key: memTime) {
                Row row = sheet.createRow(rowNum++);
                int cellNum = 0;
                for (Object obj : key) {
                    Cell cell = row.createCell(cellNum++);
                    cell.setCellStyle(FormatCell.format(workbook, FormatCell.cellColor[colorIndex]));

                    if (obj instanceof Date)
                        cell.setCellValue((Date) obj);
                    else if (obj instanceof Boolean)
                        cell.setCellValue((Boolean) obj);
                    else if (obj instanceof String)
                        cell.setCellValue((String) obj);
                    else if (obj instanceof Double)
                        cell.setCellValue((Double) obj);
                }

                if(colorIndex < memTime.length) {
                    colorIndex++;
                }else{
                    colorIndex = 0;
                }
            }

            colorIndex = 0;
            file.close();
            FileOutputStream outFile = new FileOutputStream(timetable);
            workbook.write(outFile);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * jump two rows in excel file
     */
    public void jumpTwoRows(){
        rowNum += 2;
    }
}