package Controller.Manager.ManageWorkSchedule;

import Controller.Manager.SwitchManagerHomePagePanelController;
import Model.Database.EmployeesInfo;
import View.Manager.ManagerFrame;

import javax.swing.*;

public class ConfirmEditEmployeeScheduleController {
    private ManagerFrame managerFrame;
    private EmployeesInfo employeesInfo;

    public ConfirmEditEmployeeScheduleController(ManagerFrame managerFrame, EmployeesInfo employeesInfo){
        this.managerFrame = managerFrame;
        this.employeesInfo = employeesInfo;
    }

    private boolean updateChange(Object[][] employeeSchedule){
        employeesInfo.setEmployeeWeeklySchedule(employeeSchedule);
        return true;
    }

    public JPanel updateAndReturnHomePage(Object[][] employeeSchedule){
        updateChange(employeeSchedule);
        SwitchManagerHomePagePanelController switchManagerHomePagePanelController = new SwitchManagerHomePagePanelController(managerFrame);
        return switchManagerHomePagePanelController.getManagerHomePagePanel();
    }
}
