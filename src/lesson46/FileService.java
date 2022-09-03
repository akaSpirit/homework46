package lesson46;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class FileService {
    private List<BookModel.Book> books;
    private List<EmployeeModel.Employee> employees;
    private static final Gson gson = new Gson();
    private FileService(String fileName) {
        var filePath = Path.of("data", fileName);
//        Gson gson = new Gson();
        try {
            books = List.of(gson.fromJson(Files.readString(filePath), BookModel.Book[].class));
            employees = List.of(gson.fromJson(Files.readString(filePath), EmployeeModel.Employee[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileService read(String fileName) {
        var file = new FileService(fileName);
        return file;
    }

    public List<BookModel.Book> getBooks() {
        return books;
    }

    public List<EmployeeModel.Employee> getEmployees() {
        return employees;
    }

    public static void writeEmployee(List<EmployeeModel.Employee> emp) {
        String json = gson.toJson(emp);
        try {
            byte[] arr = json.getBytes();
            Files.write(Paths.get("data/employees.json"), arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
