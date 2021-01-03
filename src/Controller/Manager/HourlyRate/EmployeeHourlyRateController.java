package Controller.Manager.HourlyRate;


import Model.Database.EmployeesInfo;

public class EmployeeHourlyRateController {
    private EmployeesInfo employeesInfo;

    public EmployeeHourlyRateController(EmployeesInfo employeesInfo){
        this.employeesInfo = employeesInfo;
    }

    public Object[][] getEmployeeHourlyRate(){
        return employeesInfo.getEmployeeHourlyRate();
    }
}
