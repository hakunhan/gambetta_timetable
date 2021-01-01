package Controller.Manager.Export;

import Model.Database.GetEmployeesInfo;
import Model.ExcelTable.EmployeeWorkTimeExcelPrinter;
import Model.ExcelTable.GetTime;

import java.io.IOException;

public class ExportScheduleController {
    private GetEmployeesInfo getEmployeesInfo;

    public ExportScheduleController(GetEmployeesInfo getEmployeesInfo){
        this.getEmployeesInfo = getEmployeesInfo;
    }

    public void exportSchedule(){
        Object[][] employeeSchedules = getEmployeesInfo.getEmployeeWeeklySchedule();
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
