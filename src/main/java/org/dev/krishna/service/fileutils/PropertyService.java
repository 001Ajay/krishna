package org.dev.krishna.service.fileutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Service
public class PropertyService {


    // TODO : Cyclic dependeny in autowiring
    @Autowired
    private FileInfoCRUDService fileInfoCRUDService;

    private static String propertyFileLocation;
    private static PropertyService instance;
    private static Map<String, String> properties;
    static {
        instance = new PropertyService();
    }
    public PropertyService getInstance(){
        return instance;
    }

    private PropertyService(){
        instance = new PropertyService();
        properties = new HashMap<>();
        List<String> lines = fileInfoCRUDService.readFile(propertyFileLocation);
        properties = lines.stream()
                .map(line -> line.split("="))
                .filter(elem -> elem.length==2)
                .collect(Collectors.toMap(line -> line[0],line -> line[1]));
    }

    public static String getProperty(String key){
        return properties.get(key);
    }
}
