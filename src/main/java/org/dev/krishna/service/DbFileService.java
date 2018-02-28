package org.dev.krishna.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class DbFileService<T>{

    private ObjectMapper mapper = new ObjectMapper();
    private static boolean APPEND_TO_FILE = true;

    protected abstract DB_FILE dBFileType();
    protected abstract Class<T> getFileTypeClass();
    public abstract String getId();

    private Path dbFilePathInstance;
    private BufferedReader bufferedReader;
    private FileReader fileReader;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;

    public DbFileService(){
        dbFilePathInstance = Paths.get(dBFileType().getPath());
    }

    public List<T> findAll() {
        try (Stream<String> stream = Files.lines(dbFilePathInstance)) {
            return stream.map(info -> {
                try {
                    return mapper.readValue(info, getFileTypeClass());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<T> find(String id) {
        String idString = getIdString(id);
        try (Stream<String> stream = Files.lines(dbFilePathInstance)) {
            return stream
                    .filter(line -> line.contains(idString))
                    .map(line -> createObject(line))
                    .findFirst()
                    .get();
        } catch (IOException e) {
            System.err.println("Object not found with id : "+id);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public String add(T object) throws Exception {
        try(FileWriter fw = new FileWriter(dBFileType().getFile(), APPEND_TO_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(fw)){
            String content = createString(object);
            bufferedWriter.write(content+"\n");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void update(T object) throws Exception {
        List<String> lines = Files.readAllLines(dbFilePathInstance);
        String objectString = createString(object);
        String id = objectString.substring(objectString.indexOf("id"));
        String idString = id.substring(0, objectString.indexOf("\","));
        List<String> editedLines = lines.stream()
                .map(line -> lines.contains(idString) ? objectString : line)
                .collect(Collectors.toList());
        Files.write(dbFilePathInstance, editedLines, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void delete(String id) throws Exception {
        List<String> lines = Files.readAllLines(dbFilePathInstance);
        String idString = getIdString(id);
        List<String> editedLines = lines.stream()
                .filter(line -> !lines.contains(idString))
                .collect(Collectors.toList());
        Files.write(dbFilePathInstance, editedLines, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private String getIdString(String id){
        return "id\":\""+id;
    }

    private Optional<T> createObject(String objectString){
        try {
            T t = mapper.readValue(objectString, getFileTypeClass());
            return Optional.of(t);
        } catch (IOException e) {
            System.err.println(" Mapping failed : "+objectString);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private String createString(T t){
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            System.err.println("String not created");
            e.printStackTrace();
        }
        return "";
    }

    private void openFileToWrite(){
        try(FileWriter fw = new FileWriter(dBFileType().getFile(), APPEND_TO_FILE)){
            bufferedWriter = new BufferedWriter(fw);
            printWriter = new PrintWriter(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeFileAfterWrite(){
        try {
            bufferedWriter.close();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFileToRead(){
        try (FileReader fr = new FileReader(dBFileType().getFile())) {
            fileReader = fr;
            bufferedReader = new BufferedReader(fileReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeFileAfterRead(){
        try {
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
