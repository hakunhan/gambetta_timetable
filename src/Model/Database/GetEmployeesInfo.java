package Model.Database;

import java.util.ArrayList;

public class GetEmployeesInfo {
    private ArrayList<Employee> employeesInfo;

    public GetEmployeesInfo(ArrayList<Employee> employeesInfo){
        this.employeesInfo = employeesInfo;
    }

    public Object[][] getEmployeeHourlyRate(){
        Object[][] result = new Object[employeesInfo.size()][2];

        for (int i = 0; i < employeesInfo.size(); i++){
            result[i][0] = employeesInfo.get(i).getName();
            result[i][1] = employeesInfo.get(i).getHourlyRate();
        }

        return result;
    }

    public Object[][] getEmployeeWeeklySchedule(){
        Object[][] result = new Object[employeesInfo.size()][8];

        for (int i = 0; i < employeesInfo.size(); i++){
            result[i][0] = employeesInfo.get(i).getName();
            result[i][1] = employeesInfo.get(i).getMonday();
            result[i][2] = employeesInfo.get(i).getTuesday();
            result[i][3] = employeesInfo.get(i).getWednesday();
            result[i][4] = employeesInfo.get(i).getThursday();
            result[i][5] = employeesInfo.get(i).getFriday();
            result[i][6] = employeesInfo.get(i).getSaturday();
            result[i][7] = employeesInfo.get(i).getSunday();
        }

        return result;
    }
}
