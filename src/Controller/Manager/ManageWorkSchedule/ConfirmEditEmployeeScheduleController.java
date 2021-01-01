package Controller.Manager.ManageWorkSchedule;

import Controller.Manager.SwitchManagerHomePagePanelController;
import Model.Database.DBUtils;
import Model.Database.ScheduleSqlStatement;
import View.Manager.ManagerFrame;

import javax.swing.*;

public class ConfirmEditEmployeeScheduleController {
    private  DBUtils database = new DBUtils();
    private ScheduleSqlStatement scheduleSqlStatement = new ScheduleSqlStatement();
    private ManagerFrame managerFrame;
    private Object[][] employeeSchedule;

    public ConfirmEditEmployeeScheduleController(ManagerFrame managerFrame, Object[][] employeeSchedule){
        this.employeeSchedule = employeeSchedule;
        this.managerFrame = managerFrame;
    }

    private Object[][] getTheChange(Object[][] employeeSchedule){
        Object[][] employeeScheduleChange = new Object[employeeSchedule.length][8];

        for (int i = 0; i < employeeScheduleChange.length; i++){
            employeeScheduleChange[i][0] = employeeSchedule[i][0];
        }

        for (int i = 0; i < employeeSchedule.length; i++){
            for (int j = 2; j < 9; j++){
                if (this.employeeSchedule[i][j] != employeeSchedule[i][j])
                    employeeScheduleChange[i][j-1] = employeeSchedule[i][j];
            }
        }

        return employeeScheduleChange;
    }

    private boolean updateChange(Object[][] employeeSchedule){
        Object[][] changes = getTheChange(employeeSchedule);

        for (Object[] change : changes) {
            for (int j = 1; j < 8; j++) {
                if (change[j] == null) {
                    continue;
                }

                switch (j) {
                    case 1:
                        scheduleSqlStatement.updateMonday(Integer.parseInt((String) change[0]), (String) change[j]);
                        break;
                    case 2:
                        scheduleSqlStatement.updateTuesday(Integer.parseInt((String) change[0]), (String) change[j]);
                        break;
                    case 3:
                        scheduleSqlStatement.updateWednesday(Integer.parseInt((String) change[0]), (String) change[j]);
                        break;
                    case 4:
                        scheduleSqlStatement.updateThursday(Integer.parseInt((String) change[0]), (String) change[j]);
                        break;
                    case 5:
                        scheduleSqlStatement.updateFriday(Integer.parseInt((String) change[0]), (String) change[j]);
                        break;
                    case 6:
                        scheduleSqlStatement.updateSaturday(Integer.parseInt((String) change[0]), (String) change[j]);
                        break;
                    case 7:
                        scheduleSqlStatement.updateSunday(Integer.parseInt((String) change[0]), (String) change[j]);
                        break;
                }
            }
        }

        return true;
    }

    public JPanel updateAndReturnHomePage(Object[][] employeeSchedule){
        updateChange(employeeSchedule);
        SwitchManagerHomePagePanelController switchManagerHomePagePanelController = new SwitchManagerHomePagePanelController(managerFrame);
        return switchManagerHomePagePanelController.getManagerHomePagePanel();
    }
}
