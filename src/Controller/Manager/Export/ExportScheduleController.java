package Controller.Manager.Export;

import Model.Database.EmployeesInfo;
import Model.ExcelTable.EmployeeWorkTimeExcelPrinter;
import Model.ExcelTable.GetTime;

import java.io.IOException;

public class ExportScheduleController {
    private EmployeesInfo employeesInfo;

    public ExportScheduleController(EmployeesInfo employeesInfo){
        this.employeesInfo = employeesInfo;
    }

    public void exportSchedule(){
        Object[][] employeeSchedules = employeesInfo.getEmployeeWeeklySchedule();
        try {
            GetTime time = new GetTime();
            EmployeeWorkTimeExcelPrinter employeeWorkTimeExcelPrinter = new EmployeeWorkTimeExcelPrinter(employeeSchedules);

            for (int i = 0; i < time.totalWeek(); i++){
                employeeWorkTimeExcelPrinter.setDate(time.getDate());
                employeeWorkTimeExcelPrinter.printDate();
                employeeWorkTimeExcelPrinter.printMemTime();
                employeeWorkTimeExcelPrinter.jumpTwoRows();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
