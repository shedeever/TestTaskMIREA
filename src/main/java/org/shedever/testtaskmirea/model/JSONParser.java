package org.shedever.testtaskmirea.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.shedever.testtaskmirea.entity.Student;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JSONParser {
    public static List<Student> parseStudents(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new FileReader(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void exportStudentsToJsonFile(List<Student> students, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(new File(filePath), students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
