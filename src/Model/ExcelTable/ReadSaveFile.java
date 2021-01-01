package Model.ExcelTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class ReadSaveFile {
    public int numOfLine = 0;

    public Object[][] readFile(){
        Object[][] readData = new Object[10][8];
        int count = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("save file.txt"))){

            for(String line; (line = br.readLine()) != null;){
                String[] read = line.split("/");
                readData[count] = read;
                count++;
                numOfLine++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return readData;
    }

    public static void main(String[] args) {
        ReadSaveFile read = new ReadSaveFile();

        Object[][] getData = read.readFile();
        for(Object[] arrData : getData){
            for(Object data: arrData){
                System.out.print(data);
            }
            System.out.println();
        }

        File file = new File(".");
        for(String fileNames : file.list()) System.out.println(fileNames);
    }
}
