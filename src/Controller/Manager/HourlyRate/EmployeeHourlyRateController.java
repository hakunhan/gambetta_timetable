package Controller.Manager.HourlyRate;


import Model.Database.GetEmployeesInfo;

public class EmployeeHourlyRateController {
    private GetEmployeesInfo getEmployeesInfo;

    public EmployeeHourlyRateController(GetEmployeesInfo getEmployeesInfo){
        this.getEmployeesInfo = getEmployeesInfo;
    }

    public Object[][] getEmployeeHourlyRate(){
        return getEmployeesInfo.getEmployeeHourlyRate();
    }
}
