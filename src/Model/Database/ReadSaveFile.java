package Model.Database;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadSaveFile {
    private File saveFile;

    public ReadSaveFile() throws IOException {
        saveFile = new File("saveFile.txt");
        if (!saveFile.exists()){
            saveFile.createNewFile();
        }
    }

    public ArrayList<Employee> getEmployeeInfo() throws IOException {
        ArrayList<Employee> employeesInfo = new ArrayList<>();
        try {
            BufferedReader employeeInfoReader = new BufferedReader(new InputStreamReader(new FileInputStream("saveFile.txt"), StandardCharsets.UTF_8));

            while (employeeInfoReader.ready()){
                String data = employeeInfoReader.readLine();
                String[] employeeData = data.split("_");


                for (int i = 0; i < employeeData.length / 9; i++){
                    Employee employee = new Employee(employeeData[i*9], Double.parseDouble(employeeData[i*9 + 1]));
                    employee.setMonday(employeeData[i*9 + 2]);
                    employee.setTuesday(employeeData[i*9 + 3]);
                    employee.setWednesday(employeeData[i*9 + 4]);
                    employee.setThursday(employeeData[i*9 + 5]);
                    employee.setFriday(employeeData[i*9 + 6]);
                    employee.setSaturday(employeeData[i*9 + 7]);
                    employee.setSunday(employeeData[i*9 + 8]);

                    employeesInfo.add(employee);
                }
            }

            return employeesInfo;

        }catch (FileNotFoundException e){
            throw new FileNotFoundException("Cant find save file!");
        }
    }
}
