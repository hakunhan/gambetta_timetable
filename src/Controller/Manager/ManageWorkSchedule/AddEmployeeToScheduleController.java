package Controller.Manager.ManageWorkSchedule;

import Model.Database.Employee;
import Model.Database.GetEmployeesInfo;
import View.Manager.ManageEmployeeSchedulePanel;
import View.Manager.ManagerFrame;
import utils.NotPossibleException;

import javax.swing.*;

public class AddEmployeeToScheduleController {
    private ManageEmployeeSchedulePanel manageEmployeeSchedulePanel;
    private ManagerFrame frame;
    private GetEmployeesInfo getEmployeesInfo;
    private String employeeName;
    private double employeeHourlyRate;

    public AddEmployeeToScheduleController(ManagerFrame frame, GetEmployeesInfo getEmployeesInfo,
                                           String employeeName, double employeeHourlyRate){
        this.frame = frame;
        this.getEmployeesInfo = getEmployeesInfo;
        this.employeeName = employeeName;
        this.employeeHourlyRate = employeeHourlyRate;
    }

    private boolean addEmployeeToSchedule(){
        Employee employee = new Employee(employeeName, employeeHourlyRate);
        getEmployeesInfo.addEmployee(employee);
        return true;
    }

    public JPanel updateEmployeeSchedule(){
        SwitchEmployeeSchedulePanelController switchEmployeeSchedulePanelController = new SwitchEmployeeSchedulePanelController(frame, getEmployeesInfo);
        return switchEmployeeSchedulePanelController.getManageEmployeeSchedulePanel();
    }
}
