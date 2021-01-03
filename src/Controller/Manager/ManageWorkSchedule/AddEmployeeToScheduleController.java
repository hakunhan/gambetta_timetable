package Controller.Manager.ManageWorkSchedule;

import Model.Database.Employee;
import Model.Database.EmployeesInfo;
import View.Manager.ManageEmployeeSchedulePanel;
import View.Manager.ManagerFrame;

import javax.swing.*;

public class AddEmployeeToScheduleController {
    private ManageEmployeeSchedulePanel manageEmployeeSchedulePanel;
    private ManagerFrame frame;
    private EmployeesInfo employeesInfo;
    private Object[] newEmployee;

    public AddEmployeeToScheduleController(ManagerFrame frame, EmployeesInfo employeesInfo, Object[] newEmployee){
        this.frame = frame;
        this.employeesInfo = employeesInfo;
        this.newEmployee = newEmployee;
    }

    private boolean addEmployeeToSchedule(){
        Employee employee = new Employee((String) newEmployee[0], Double.parseDouble((String) newEmployee[1]));
        employeesInfo.addEmployee(employee);
        return true;
    }

    public JPanel updateEmployeeSchedule(){
        addEmployeeToSchedule();
        SwitchEmployeeSchedulePanelController switchEmployeeSchedulePanelController = new SwitchEmployeeSchedulePanelController(frame, employeesInfo);
        return switchEmployeeSchedulePanelController.getManageEmployeeSchedulePanel();
    }
}
