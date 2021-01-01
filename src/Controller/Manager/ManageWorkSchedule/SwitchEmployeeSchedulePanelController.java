package Controller.Manager.ManageWorkSchedule;

import Model.Database.GetEmployeesInfo;
import View.Manager.ManageEmployeeSchedulePanel;
import View.Manager.ManagerFrame;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SwitchEmployeeSchedulePanelController {
    private ManagerFrame frame;
    private GetEmployeesInfo getEmployeesInfo;

    public SwitchEmployeeSchedulePanelController(ManagerFrame frame, GetEmployeesInfo getEmployeesInfo){
        if (frame == null){
            throw new NullPointerException("Frame is null");
        }
        this.getEmployeesInfo = getEmployeesInfo;
        this.frame = frame;
    }

    private Object[][] updateManageEmployeeSchedulePanel(){
        Object[][] employeeWeeklySchedules = getEmployeesInfo.getEmployeeWeeklySchedule();
        Object[][] employeeSchedules = new Object[employeeWeeklySchedules.length][9];
        for (int i = 0; i < employeeWeeklySchedules.length; i++){
            employeeSchedules[i] = employeeWeeklySchedules[i];
            employeeSchedules[i][8] = "\u00D8";
        }

        return employeeSchedules;
    }

    public JPanel getManageEmployeeSchedulePanel(){
        return new ManageEmployeeSchedulePanel(updateManageEmployeeSchedulePanel(), frame);
    }
}
