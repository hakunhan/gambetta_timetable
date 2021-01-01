package Controller.Manager.Export;

import Model.Database.DBUtils;
import Model.Database.ScheduleSqlStatement;
import Model.ExcelTable.EmployeeWorkTimeExcelPrinter;
import Model.ExcelTable.GetTime;

import java.io.IOException;

public class ExportScheduleController {
    private DBUtils dbUtils;
    private ScheduleSqlStatement scheduleSqlStatement;

    public ExportScheduleController(){
        dbUtils = new DBUtils();
        scheduleSqlStatement = new ScheduleSqlStatement();
    }

    private int[] getEmployeeId(){
        Object[] employeeId = scheduleSqlStatement.getEmployeeId();
        int[] result = new int[employeeId.length];

        for(int i = 0; i < result.length; i++){
            result[i] = (int) employeeId[i];
        }

        return result;
    }

    private Object[][] getEmployeeSchedules(){
        int[] employeeId = getEmployeeId();
        Object[][] result = new Object[employeeId.length][8];

        for (int i = 0; i < employeeId.length; i++){
            Object[] employeeSchedule = scheduleSqlStatement.getScheduleEmployeeWithOutId(employeeId[i]);
            result[i] = employeeSchedule;
        }

        return result;
    }

    public void exportSchedule(){
        Object[][] employeeSchedules = getEmployeeSchedules();
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
