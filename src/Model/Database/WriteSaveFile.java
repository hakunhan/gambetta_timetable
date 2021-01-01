package Model.Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class WriteSaveFile {
    private File saveFile;
    public WriteSaveFile() {
        File mkDir = new File("\\saveFile");
        if (!mkDir.exists()){
            mkDir.mkdirs();
            saveFile = new File("\\saveFile\\saveFile.txt");
            if(!saveFile.exists()){
                try {
                    saveFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateSaveFile(String[] lines){
        try{
            FileWriter writer = new FileWriter(saveFile);
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
            FileWriter writer = new FileWriter(saveFile, true);
            writer.write(line);
            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
