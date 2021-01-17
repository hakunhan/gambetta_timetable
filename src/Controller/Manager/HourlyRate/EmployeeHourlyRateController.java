package Controller.Manager.HourlyRate;


import Model.Database.EmployeesInfo;
import utils.NotPossibleException;

public class EmployeeHourlyRateController {
    private EmployeesInfo employeesInfo;

    public EmployeeHourlyRateController(EmployeesInfo employeesInfo){
        this.employeesInfo = employeesInfo;
    }

    public Object[][] getEmployeeHourlyRate(){
        return employeesInfo.getEmployeeHourlyRate();
    }

    public void updateEmployeeHourlyRate(Object[][] newHourlyRate) throws NotPossibleException {
        employeesInfo.setEmployeeHourlyRate(newHourlyRate);
    }
}
