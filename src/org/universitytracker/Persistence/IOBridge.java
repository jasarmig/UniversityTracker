package org.universitytracker.Persistence;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class IOBridge{

    private String basePath = "C:\\Users\\je.sarmiento\\Documents\\TAAU2\\UniversityTracker\\src\\org\\universitytracker\\Persistence\\";

    public IOBridge(String file){
        basePath += file;
    }

    public int countRegisters() throws IOException {
        int id = 1;
        BufferedReader reader = new BufferedReader(new FileReader(basePath));
        while (reader.readLine() != null) id++;
        reader.close();
        return id;
    }

    public void toFile(Object object) throws IOException {
        FileWriter writer = new FileWriter(basePath, true);
        writer.write(object.toString());
        writer.close();
    }

    public ArrayList<String> fromFile() throws IOException {
        ArrayList<String> outputList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(basePath));
        String line;
        while((line = reader.readLine()) != null){
            outputList.add(line);
        }
        return outputList;
    }

    public void updateCourse(String update, int courseId) throws IOException{
        update = update.replaceAll("\\n","");
        BufferedReader reader = new BufferedReader(new FileReader(basePath));
        StringBuilder inputBuffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            if(courseId == parseInt(String.valueOf(line.charAt(0)))){
                line = update;
            }
            inputBuffer.append(line);
            inputBuffer.append("\n");
        }
        reader.close();

        FileOutputStream fileOut = new FileOutputStream(basePath);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();
    }
}
