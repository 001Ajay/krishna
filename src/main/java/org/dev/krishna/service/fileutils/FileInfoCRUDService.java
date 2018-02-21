package org.dev.krishna.service.fileutils;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class FileInfoCRUDService {

    private static String dbFileLocation = "D:\\fileInfoDb.txt";
    private static File dbFile;
    private static int coreCount = 4;
    private static FileWriter fr = null;
    private static BufferedWriter br = null;

    public FileInfoCRUDService(){
        try{
            dbFile = new File(dbFileLocation);
            if(!dbFile.exists())
                dbFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeLine(String info){
        String dataWithNewLine=info+System.getProperty("line.separator");
        init();
        try {
            br.write(dataWithNewLine);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }

    private void init(){
        try{
            fr = new FileWriter(dbFile);
            br = new BufferedWriter(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close(){
        try {
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(FileInfo fileInfo){
        writeLine(fileInfo.toString());
    }

    public List<String> readFile(String fileLocation){
        File file = new File(fileLocation);
        try (FileInputStream fis = new FileInputStream(file)) {
            int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                System.out.print((char) content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
