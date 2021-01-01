package Controller.Manager.ManageWorkSchedule;


import Model.Database.GetEmployeesInfo;
import View.Manager.ManagerFrame;

import javax.swing.*;

public class RemoveEmployeeScheduleController {
    private JPanel manageEmployeeSchedulePanel;
    private GetEmployeesInfo getEmployeesInfo;
    private ManagerFrame frame;
    private String employeeName;

    public RemoveEmployeeScheduleController(ManagerFrame frame, GetEmployeesInfo getEmployeesInfo, String employeeName){
        this.frame = frame;
        this.getEmployeesInfo = getEmployeesInfo;
        this.employeeName = employeeName;
    }

    private void removeEmployeeSchedule(){
        getEmployeesInfo.removeEmployee(employeeName);
    }

    public JPanel updateEmployeeSchedule(){
        removeEmployeeSchedule();
        SwitchEmployeeSchedulePanelController switchEmployeeSchedulePanelController = new SwitchEmployeeSchedulePanelController(frame, getEmployeesInfo);
        return switchEmployeeSchedulePanelController.getManageEmployeeSchedulePanel();
    }
}
