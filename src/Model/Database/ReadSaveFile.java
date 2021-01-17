package Model.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public ArrayList<Employee> getEmployeeInfo() throws FileNotFoundException {
        ArrayList<Employee> employeesInfo = new ArrayList<>();
        try {
            Scanner employeeInfoReader = new Scanner(saveFile);

            while (employeeInfoReader.hasNextLine()){
                String data = employeeInfoReader.nextLine();
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
