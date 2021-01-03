package Controller.Manager.ManageWorkSchedule;

import Model.Database.EmployeesInfo;
import View.Manager.ManageEmployeeSchedulePanel;
import View.Manager.ManagerFrame;

import javax.swing.*;

public class SwitchEmployeeSchedulePanelController {
    private ManagerFrame frame;
    private EmployeesInfo employeesInfo;

    public SwitchEmployeeSchedulePanelController(ManagerFrame frame, EmployeesInfo employeesInfo){
        if (frame == null){
            throw new NullPointerException("Frame is null");
        }
        this.employeesInfo = employeesInfo;
        this.frame = frame;
    }

    private Object[][] updateManageEmployeeSchedulePanel(){
        Object[][] employeeWeeklySchedules = employeesInfo.getEmployeeWeeklySchedule();
        Object[][] employeeSchedules = new Object[employeeWeeklySchedules.length][9];
        for (int i = 0; i < employeeWeeklySchedules.length; i++){
            employeeSchedules[i] = employeeWeeklySchedules[i];
            employeeSchedules[i][8] = "\u00D8";
        }

        return employeeSchedules;
    }

    public JPanel getManageEmployeeSchedulePanel(){
        return new ManageEmployeeSchedulePanel(updateManageEmployeeSchedulePanel(),frame, employeesInfo);
    }
}
