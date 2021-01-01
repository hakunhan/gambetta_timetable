package Controller.Manager.ManageWorkSchedule;

import Model.Database.DBUtils;
import Model.Database.EmployeeSqlStatement;
import Model.Database.ScheduleSqlStatement;
import View.Manager.ManageEmployeeSchedulePanel;
import View.Employee.EmployeeFrame;
import View.Manager.ManagerFrame;
import utils.NotPossibleException;

import javax.swing.*;

public class AddEmployeeToScheduleController {
    private ManageEmployeeSchedulePanel manageEmployeeSchedulePanel;
    private DBUtils database = new DBUtils();
    private ManagerFrame frame;
    private int employee_id;
    private ScheduleSqlStatement scheduleSqlStatement = new ScheduleSqlStatement();
    private EmployeeSqlStatement employeeSqlStatement = new EmployeeSqlStatement();

    public AddEmployeeToScheduleController(ManagerFrame frame, int employee_id){
        this.frame = frame;
        this.employee_id = employee_id;
    }

    private boolean addEmployeeToSchedule(){
        Object[] arr = scheduleSqlStatement.getEmployeeId();

        for (int i = 0; i < arr.length;i++){
            if (employee_id == (int) arr[i])
                return false;
        }
        EmployeeSqlStatement employeeSqlStatement = new EmployeeSqlStatement();
        Object[] checkEmployee = employeeSqlStatement.getEmployee(employee_id);

        if (checkEmployee[0] == null){
            return false;
        }

        scheduleSqlStatement.insertIntoSchedule(employee_id, new String[8]);
        return true;
    }

    public JPanel updateEmployeeSchedule(){
        if (!addEmployeeToSchedule())
            throw new NotPossibleException("Cannot add employee");
        SwitchEmployeeSchedulePanelController switchEmployeeSchedulePanelController = new SwitchEmployeeSchedulePanelController(frame);
        return switchEmployeeSchedulePanelController.getManageEmployeeSchedulePanel();
    }
}
