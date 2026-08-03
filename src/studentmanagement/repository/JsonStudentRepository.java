package studentmanagement.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import studentmanagement.model.Student;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonStudentRepository {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static void save(List<Student> students) {
        try {
            File file = new File("students.json");
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file,students);
        } catch (IOException e) {
           System.out.println("Unable to save file");
           System.out.println("Please try again!");
        }
    }

    public static List<Student> load() {
        File file = new File("students.json");

        if(!file.exists()) return new ArrayList<>();
        try {
            return mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}