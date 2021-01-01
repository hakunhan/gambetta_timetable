package Model.ExcelTable;

import java.io.*;

import org.apache.poi.POIXMLException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class CalculateSalary {
    private ReadEmployeeExcelFile readEmployeeExcelFile;
    private String employeeName;

    /**
     *  constructor that create excel .xlsx file and set rows height and columns width
     */
    public CalculateSalary(String employeeName, ReadEmployeeExcelFile readEmployeeExcelFile) throws IOException {
        this.employeeName = employeeName;
        this.readEmployeeExcelFile = readEmployeeExcelFile;

    }

    public float calculateSalary(float hourlyRate) throws POIXMLException, IOException {
        Object[][] employeeWorkInMonth = readEmployeeExcelFile.employeeWorkInMonth(this.employeeName);
        Object[][] result = new Object[employeeWorkInMonth.length][3];
        float totalTime = 0;

        int count = 0;
        for (Object[] _temp: employeeWorkInMonth){
            if (_temp[0] == null || _temp[1] == null)
                break;

            result[count][0] = _temp[0];
            result[count][1] = _temp[1];

            Object[] shiftWork = _temp[1].toString().split("-");
            Object[] beginShiftWork = shiftWork[0].toString().split(":");
            Object[] endShiftWork = shiftWork[1].toString().split(":");
            float beginTime = Float.parseFloat(beginShiftWork[0].toString().trim()) + Float.parseFloat(beginShiftWork[1].toString().trim())/60;
            float endTime = Float.parseFloat(endShiftWork[0].toString().trim()) + Float.parseFloat(beginShiftWork[1].toString().trim())/60;

            totalTime += endTime - beginTime;

            count++;
        }

        return totalTime * hourlyRate;
    }
}
