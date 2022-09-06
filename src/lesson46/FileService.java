package lesson46;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class FileService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path booksPath = Paths.get("data/books2.json");
    private static final Path employeesPath = Paths.get("data/employees2.json");

    public static List<DataModel.Book> readBooks() {
        var type = new TypeToken<List<DataModel.Book>>() {}.getType();
        String json = "";
        try {
            json = Files.readString(booksPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GSON.fromJson(json, type);
    }

    public static void writeBooks(List<DataModel.Book> emp) {
        String json = GSON.toJson(emp);
        try {
            byte[] arr = json.getBytes();
            Files.write(booksPath, arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, DataModel.Employee> readEmployees() {
        var type = new TypeToken<Map<String, DataModel.Employee>>() {}.getType();
        String json = "";
        try {
            json = Files.readString(employeesPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GSON.fromJson(json, type);
    }

    public static void writeEmployees(Map<String, DataModel.Employee> employeeMap) {
        String json = GSON.toJson(employeeMap);
        try {
            byte[] arr = json.getBytes();
            Files.write(employeesPath, arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
