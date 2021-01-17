package Model.Database;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class WriteSaveFile {
    private File saveFile;
    public WriteSaveFile() {
        saveFile = new File("saveFile.txt");
        if(!saveFile.exists()){
            try {
                saveFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void updateSaveFile(String[] lines){
        try{
            if (lines.length == 0 || lines == null){
                saveFile.createNewFile();
                return;
            }
            Writer writer = new OutputStreamWriter(new FileOutputStream(saveFile), StandardCharsets.UTF_8);
            for (String line: lines){
                writer.write(line);
            }
            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void addNewLine(String line){
        try{
            Writer writer = new OutputStreamWriter(new FileOutputStream(saveFile, true), StandardCharsets.UTF_8);
            writer.write(line);
            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
