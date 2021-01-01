package Controller.Manager.ManageWorkSchedule;

import Model.Database.DBUtils;
import Model.Database.ScheduleSqlStatement;
import View.Employee.EmployeeFrame;
import View.Manager.ManagerFrame;

import javax.swing.*;

public class RemoveEmployeeScheduleController {
    private JPanel manageEmployeeSchedulePanel;
    private DBUtils database = new DBUtils();
    private ManagerFrame frame;
    private int employee_id;

    public RemoveEmployeeScheduleController(ManagerFrame frame, int employee_id){
        this.frame = frame;
        this.employee_id = employee_id;
    }

    private void removeEmployeeSchedule(){
        ScheduleSqlStatement scheduleSqlStatement = new ScheduleSqlStatement();
        scheduleSqlStatement.deleteSchedule(employee_id);
    }

    public JPanel updateEmployeeSchedule(){
        removeEmployeeSchedule();
        SwitchEmployeeSchedulePanelController switchEmployeeSchedulePanelController = new SwitchEmployeeSchedulePanelController(frame);
        return switchEmployeeSchedulePanelController.getManageEmployeeSchedulePanel();
    }
}
