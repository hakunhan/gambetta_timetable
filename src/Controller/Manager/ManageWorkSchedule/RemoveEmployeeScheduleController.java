package Controller.Manager.ManageWorkSchedule;


import Model.Database.EmployeesInfo;
import View.Manager.ManagerFrame;

import javax.swing.*;

public class RemoveEmployeeScheduleController {
    private EmployeesInfo employeesInfo;
    private ManagerFrame frame;
    private String employeeName;

    public RemoveEmployeeScheduleController(ManagerFrame frame, EmployeesInfo employeesInfo, String employeeName){
        this.frame = frame;
        this.employeesInfo = employeesInfo;
        this.employeeName = employeeName;
    }

    private void removeEmployeeSchedule(){
        employeesInfo.removeEmployee(employeeName);
    }

    public JPanel updateEmployeeSchedule(){
        removeEmployeeSchedule();
        SwitchEmployeeSchedulePanelController switchEmployeeSchedulePanelController = new SwitchEmployeeSchedulePanelController(frame, employeesInfo);
        return switchEmployeeSchedulePanelController.getManageEmployeeSchedulePanel();
    }
}
