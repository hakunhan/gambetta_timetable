package Controller.Manager.Export;

import Model.Database.GetEmployeesInfo;
import Model.ExcelTable.EmployeeSalaryExcelPrinter;
import Model.ExcelTable.ReadEmployeeExcelFile;

import java.io.IOException;

public class ExportSalaryController {
    private GetEmployeesInfo employeesInfo;

    public ExportSalaryController(GetEmployeesInfo employeesInfo){
        this.employeesInfo = employeesInfo;
    }

    public void exportEmployeeSalary(){
        Object[][] employeesHourlyRate = employeesInfo.getEmployeeHourlyRate();
        ReadEmployeeExcelFile readEmployeeExcelFile = new ReadEmployeeExcelFile();

        try {
            for (int i = 0; i < employeesHourlyRate.length; i++) {
                EmployeeSalaryExcelPrinter employeeSalaryExcelPrinter = new EmployeeSalaryExcelPrinter((String) employeesHourlyRate[i][0], readEmployeeExcelFile);
                employeeSalaryExcelPrinter.printSalaryTable((double) employeesHourlyRate[i][1]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
