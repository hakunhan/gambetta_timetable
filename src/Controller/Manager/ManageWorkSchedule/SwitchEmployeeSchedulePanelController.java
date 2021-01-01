package Controller.Manager.ManageWorkSchedule;

import Model.Database.DBUtils;
import Model.Database.ScheduleSqlStatement;
import View.Manager.ManageEmployeeSchedulePanel;
import View.Manager.ManagerFrame;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SwitchEmployeeSchedulePanelController {
    private  DBUtils database = new DBUtils();
    private ManagerFrame frame;
    public SwitchEmployeeSchedulePanelController(ManagerFrame frame){
        if (frame == null){
            throw new NullPointerException("Frame is null");
        }
        this.frame = frame;
    }

    private Object[][] updateManageEmployeeSchedulePanel(){
        ScheduleSqlStatement scheduleSqlStatement = new ScheduleSqlStatement();
        String statement = "SELECT COUNT(employee_id) FROM schedule;";
        ResultSet countEmployee = database.retrieveData(statement);
        int numberOfEmployee = 0;

        try {
            if (countEmployee.next()){
                numberOfEmployee = countEmployee.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        Object[][] employeeSchedules = new Object[numberOfEmployee][10];
        Object[] employeeID = new ScheduleSqlStatement().getEmployeeId();

        for (int i = 0; i < numberOfEmployee; i++){
            employeeSchedules[i] = scheduleSqlStatement.getScheduleEmployeeWithId((int) employeeID[i]);
            employeeSchedules[i][9] = "\u00D8";
        }

        return employeeSchedules;
    }

    public JPanel getManageEmployeeSchedulePanel(){
        return new ManageEmployeeSchedulePanel(updateManageEmployeeSchedulePanel(), frame);
    }
}
