package org.dev.krishna.service.fileutils;



import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileUtilsService {

    public List<FileInfo> scanDirectory(String location){
        File file = new File(location);
        List<FileInfo> list = new ArrayList<>();
        getDirectories(file, list);
        return list;
    }

    private void getDirectories(File dir, List<FileInfo> fileInfoList){
        File[] files = dir.listFiles();
        Arrays.stream(files).forEach(file -> {
            if(file.isDirectory()) getDirectories(file, fileInfoList);
            else {
                FileInfo fileInfo = new FileInfo.FileInfoBuilder()
                        .name(file.getName())
                        .extension(getExtension(file))
                        .path(file.getPath())
                        .location(file.getParent())
                        .size(String.valueOf(file.length()))
                        .build();
                //System.out.println(fileInfo.toString());
                fileInfoList.add(fileInfo);
            }
        });
    }

    private static String getExtension(File file){
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    public List<Pair> checkDuplicatesInFolder(String location){
        List<Pair> info = new ArrayList<>();
        List<FileInfo> fileInfos = scanDirectory(location);
        fileInfos.forEach(file1 -> {
            fileInfos.forEach(file2 -> {
                if(!file2.getName().equals(file1.getName())){
                    try {
                        if(checkDuplicate(file1, file2)){
                            info.add(new Pair(file1,file2));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        return info;
    }

    public boolean checkDuplicate(FileInfo file1, FileInfo file2) throws Exception{
        //System.out.println(file1.getName()+" & "+file2.getName());
        boolean flag = false;
        if(!extensionMatch(file1, file2)) return flag;
        //else System.out.print(" - Extension match");
        if(!sizeMatch(file1,file2)) return flag;
        //else System.out.println(" - Size Match");
        if(!isSame(new File(file1.getPath()), new File(file2.getPath()))) return false;

        return true;
    }

    private boolean extensionMatch(FileInfo f1, FileInfo f2){
        //System.out.print(" - Extension : "+f1.getExtension()+" , "+f2.getExtension());
        return f1.getExtension().equals(f2.getExtension());
    }

    private boolean sizeMatch(FileInfo f1, FileInfo f2){
        //System.out.print(" - Size : "+f1.getSize()+" , "+f2.getSize());
        return f1.getSize().equals(f2.getSize());
    }

    public static boolean isSame(File file1, File file2) throws IOException {

        InputStream input1 = new FileInputStream(file1);
        InputStream input2 = new FileInputStream(file2);
        boolean error = false;
        try {
            byte[] buffer1 = new byte[1024];
            byte[] buffer2 = new byte[1024];
            try {
                int numRead1 = 0;
                int numRead2 = 0;
                while (true) {
                    numRead1 = input1.read(buffer1);
                    numRead2 = input2.read(buffer2);
                    if (numRead1 > -1) {
                        if (numRead2 != numRead1) return false;
                        // Otherwise same number of bytes read
                        if (!Arrays.equals(buffer1, buffer2)) return false;
                        // Otherwise same bytes read, so continue ...
                    } else {
                        // Nothing more in stream 1 ...
                        return numRead2 < 0;
                    }
                }
            } finally {
                input1.close();
            }
        } catch (IOException e) {
            error = true; // this error should be thrown, even if there is an error closing stream 2
            throw e;
        } catch (RuntimeException e) {
            error = true; // this error should be thrown, even if there is an error closing stream 2
            throw e;
        } finally {
            try {
                input2.close();
            } catch (IOException e) {
                if (!error) throw e;
            }
        }
    }
}
