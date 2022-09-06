package lesson46;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

//public class UserFileService {
//    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
//    private static final Path PATH = Paths.get("data/register.json");
//
//    public static Map<String, EmployeeModel.Employee> readJson() {
//        var type = new TypeToken<Map<String, EmployeeModel.Employee>>() {}.getType();
//        String json = "";
//        try {
//            json = Files.readString(PATH);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return GSON.fromJson(json, type);
//    }
//
//    public static void writeJson(Map<String, EmployeeModel.Employee> users) {
//        String json = GSON.toJson(users);
//        try {
//            byte[] arr = json.getBytes();
//            Files.write(PATH, arr);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}