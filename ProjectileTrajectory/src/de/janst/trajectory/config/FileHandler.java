package de.janst.trajectory.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
	
    public static ArrayList<String> read(File file) {
    	ArrayList<String> lines = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader(file);

            String line = null;
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }    

            bufferedReader.close();            
        }
        catch(FileNotFoundException ex) {
        	ex.printStackTrace();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        return lines;
    }
    
    public static void write(ArrayList<String> lines, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(String line : lines) {
            	bufferedWriter.write(line);
            	bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}


