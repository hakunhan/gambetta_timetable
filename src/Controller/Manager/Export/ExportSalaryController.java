package Controller.Manager.Export;

import Controller.Manager.HourlyRate.HourlyRateController;
import Model.Database.DBUtils;
import Model.Database.ScheduleSqlStatement;
import Model.Database.SalarySqlStatement;
import Model.ExcelTable.EmployeeSalaryExcelPrinter;
import Model.ExcelTable.ReadEmployeeExcelFile;

import java.io.IOException;

public class ExportSalaryController {
    private DBUtils dbUtils;
    private ScheduleSqlStatement scheduleSqlStatement;
    private SalarySqlStatement salarySqlStatement;

    public ExportSalaryController(){
        dbUtils = new DBUtils();
        salarySqlStatement = new SalarySqlStatement();
        scheduleSqlStatement = new ScheduleSqlStatement();
    }

    private String[] getEmployeeName(){
        Object[] employeeName = scheduleSqlStatement.getEmployeeInScheduleName();
        String[] result = new String[employeeName.length];

        for (int i = 0; i < result.length; i++){
            result[i] =(String) employeeName[i];
        }
        return result;
    }

    public void exportEmployeeSalary(){
        String[] employeeName = getEmployeeName();
        ReadEmployeeExcelFile readEmployeeExcelFile = new ReadEmployeeExcelFile();
        HourlyRateController hourlyRateController = new HourlyRateController();
        float hourlyRate = hourlyRateController.getHourlyRate();

        try {
            for (int i = 0; i < employeeName.length; i++) {
                EmployeeSalaryExcelPrinter employeeSalaryExcelPrinter = new EmployeeSalaryExcelPrinter(employeeName[i], readEmployeeExcelFile);
                employeeSalaryExcelPrinter.printSalaryTable(hourlyRate);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
