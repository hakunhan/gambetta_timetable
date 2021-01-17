package Model.Database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class EmployeesInfo {
    private ArrayList<Employee> employeesInfo;

    public EmployeesInfo() throws IOException {
        employeesInfo = new ReadSaveFile().getEmployeeInfo();
    }

    public String[] getEmployeesName(){
        String[] employeesName = new String[employeesInfo.size()];

        for (int i = 0; i < employeesInfo.size(); i++){
            employeesName[i] = employeesInfo.get(i).getName();
        }

        return employeesName;
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
        Object[][] result = new Object[employeesInfo.size()][9];

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

    public void setEmployeeWeeklySchedule(Object[][] employeeSchedule){
        for (int i = 0; i < employeesInfo.size(); i++){
            employeesInfo.get(i).setMonday((String) employeeSchedule[i][1]);
            employeesInfo.get(i).setTuesday((String) employeeSchedule[i][2]);
            employeesInfo.get(i).setWednesday((String) employeeSchedule[i][3]);
            employeesInfo.get(i).setThursday((String) employeeSchedule[i][4]);
            employeesInfo.get(i).setFriday((String) employeeSchedule[i][5]);
            employeesInfo.get(i).setSaturday((String) employeeSchedule[i][6]);
            employeesInfo.get(i).setSunday((String) employeeSchedule[i][7]);
        }
        updateEmployeeInfo();
    }

    public ArrayList<Employee> getEmployeesInfo() {
        return employeesInfo;
    }

    public void setEmployeesInfo(ArrayList<Employee> employeesInfo) {
        this.employeesInfo = employeesInfo;
    }

    public void addEmployee(Employee employee){
        employeesInfo.add(employee);

        WriteSaveFile writeSaveFile = new WriteSaveFile();
        writeSaveFile.addNewLine(employee.toString());
    }

    public void removeEmployee(String employeeName){
        for(int i = 0; i < employeesInfo.size(); i++){
            if (employeesInfo.get(i).getName().equals(employeeName))
                employeesInfo.remove(i);
        }

        updateEmployeeInfo();
    }

    public void updateEmployeeInfo(){
        String[] info = new String[employeesInfo.size()];

        for (int i = 0; i < info.length; i++){
            info[i] = employeesInfo.get(i).toString();
        }

        WriteSaveFile writeSaveFile = new WriteSaveFile();
        writeSaveFile.updateSaveFile(info);
    }
}
