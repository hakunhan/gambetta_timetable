package Model.ExcelTable;

import java.io.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class EmployeeSalaryExcelPrinter {
    private String employeeName;
    private GetTime time = new GetTime();
    private String month = time.getMonth();
    private int year = time.getYear();
    private File timetable;
    private int rowNum = 0;
    private ReadEmployeeExcelFile readEmployeeExcelFile;
    private double totalHour;

    /**
     *  constructor that create excel .xlsx file and set rows height and columns width
     */
    public EmployeeSalaryExcelPrinter(String employeeName, ReadEmployeeExcelFile readEmployeeExcelFile) throws IOException {
        this.readEmployeeExcelFile = readEmployeeExcelFile;
        this.employeeName = employeeName;
        this.timetable = new File(employeeName + "-" + month + "-" + year +".xlsx");

        GetTime time = new GetTime();
        String month = time.getMonth();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet 1");

        //setting columnWidth
        sheet.setColumnWidth(1, 5600);
        sheet.setColumnWidth(2, 5600);
        sheet.setColumnWidth(3, 5600);
        sheet.setColumnWidth(4, 5600);

        this.rowNum = 0;
        int cellNum = 1;
        Row row = sheet.createRow(++rowNum);
        Cell cell_one = row.createCell(cellNum++);
        cell_one.setCellStyle(FormatCell.format(workbook,FormatCell.cellColor[6]));
        cell_one.setCellValue("NAME");
        Cell cell_two = row.createCell(cellNum++);
        cell_two.setCellStyle(FormatCell.format(workbook,FormatCell.cellColor[6]));
        cell_two.setCellValue("DAY");
        Cell cell_three = row.createCell(cellNum++);
        cell_three.setCellStyle(FormatCell.format(workbook,FormatCell.cellColor[6]));
        cell_three.setCellValue("TIME");
        Cell cell_four = row.createCell(cellNum++);
        cell_four.setCellStyle(FormatCell.format(workbook,FormatCell.cellColor[6]));
        cell_four.setCellValue("HOUR");

        sheet.setDefaultRowHeight((short) 400);
        FileOutputStream outputStream = new FileOutputStream(employeeName+ "-" + month+ "-" + year + ".xlsx");
        workbook.write(outputStream);
        outputStream.close();
    }

    private Object[][] calculateEmployeeWorkHours() throws IOException {
        Object[][] employeeWorkInMonth = readEmployeeExcelFile.employeeWorkInMonth(this.employeeName);
        Object[][] result = new Object[employeeWorkInMonth.length][3];
        double totalTime = 0;

        int count = 0;
        for (Object[] _temp: employeeWorkInMonth){
            if (_temp[0] == null || _temp[1] == null || _temp[1] == "")
                continue;

            result[count][0] = _temp[0];
            result[count][1] = _temp[1];

            Object[] shiftWork = _temp[1].toString().split("-");
            Object[] beginShiftWork = shiftWork[0].toString().split(":");
            Object[] endShiftWork = shiftWork[1].toString().split(":");
            double beginTime = Float.parseFloat(beginShiftWork[0].toString().trim()) + Float.parseFloat(beginShiftWork[1].toString().trim())/60;
            double endTime = Float.parseFloat(endShiftWork[0].toString().trim()) + Float.parseFloat(beginShiftWork[1].toString().trim())/60;

            totalTime += endTime - beginTime;
            result[count][2] = endTime - beginTime;

            count++;
        }

        this.totalHour = totalTime;
        return result;
    }


    public void printSalaryTable(double hourlyRate) throws IOException {
        Object[][] employeeWorkHours = calculateEmployeeWorkHours();
        try {
            FileInputStream file = new FileInputStream(timetable);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Object[] key : employeeWorkHours) {
                if (key[0] == null){
                    break;
                }
                Row row = sheet.createRow(++rowNum);
                int cellNum = 2;
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
            }

            for (int i = 2; i <= (2 +readEmployeeExcelFile.getEmployeeWorkDay()- 1);i++){
                Row temp_row = sheet.getRow(i);
                Cell temp_cell = temp_row.createCell(1);
                temp_cell.setCellStyle(FormatCell.format(workbook));
                if(i == 2){
                    temp_cell.setCellValue(employeeName);
                }
            }
            if (readEmployeeExcelFile.getEmployeeWorkDay() > 1)
                sheet.addMergedRegion(new CellRangeAddress(2, 2 + readEmployeeExcelFile.getEmployeeWorkDay() -1, 1,1));

            //create a total hour cells in excel file that show the total hour that the employee work
            Row total_hour_row = sheet.createRow(++rowNum);
            Cell totalCell = total_hour_row.createCell(3);
            totalCell.setCellStyle(FormatCell.format(workbook,FormatCell.cellColor[6]));
            totalCell.setCellValue("TOTAL HOUR");
            Cell totalHourCell = total_hour_row.createCell(4);
            totalHourCell.setCellStyle(FormatCell.format(workbook));
            totalHourCell.setCellValue(totalHour);

            //create a total salary cells that show the total salary base on the total hour that the employee work
            //multiply with hourly rate
            //e.g.  total hour = 30
            //      hourly rate = 20 => salary = total hour * hourly rate = 600 (in thousand of VND)
            Row total_salary_row = sheet.createRow(++rowNum);
            Cell salaryCell = total_salary_row.createCell(3);
            salaryCell.setCellStyle(FormatCell.format(workbook,FormatCell.cellColor[6]));
            salaryCell.setCellValue("SALARY");
            Cell totalSalaryCell = total_salary_row.createCell(4);
            totalSalaryCell.setCellStyle(FormatCell.format(workbook));
            totalSalaryCell.setCellValue(totalHour*hourlyRate);

            FileOutputStream outFile = new FileOutputStream(timetable);
            workbook.write(outFile);
            outFile.close();

        } catch(IOException e){
                e.printStackTrace();
        }
    }
}
