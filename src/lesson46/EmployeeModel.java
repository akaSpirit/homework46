//<#list users as k, v>
//        <#list employees as employee>
//        <#if v == employee>
//        <tr>
//        <th scope="row">${k}</th>
//        <td>${v.empID}</td>
//        <td>${v.empID}</td>
//        <td>${v.firstName}</td>
//        <td>${v.lastName}</td>
//        <td>${v.username}</td>
//        <td>${v.password}</td>
//
//        </tr>
//        </#if>
//        </#list>
//        </#list>

package lesson46;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//public class EmployeeModel {
//    private List<Employee> employees = FileService.read("employees.json").getEmployees();
//    private List<BookModel.Book> books = FileService.read("books.json").getBooks();

//    private Map<String, Employee> users = UserFileService.readJson();
//    public Map<String, Employee> getUsers() {
//        return users;
//    }
//    public List<Employee> getEmployees() {
//        return employees;
//    }

//    public void setEmployees(List<Employee> employees) {
//        this.employees = employees;
//    }



//    public static class Employee {
////        private int empID;
//        private String email;
//        private String firstName;
//        private String lastName;
//        private String username;
//        private String password;
////        private int[] readID;
////        private int[] readNowID;
//
//        public Employee() {
//        }

//        public Employee(int empID, String firstName, String lastName) {
//            this.empID = empID;
//            this.firstName = firstName;
//            this.lastName = lastName;
//        }
//
//        public Employee(int empID, String firstName, String lastName, String username, String password) {
//            this.empID = empID;
//            this.firstName = firstName;
//            this.lastName = lastName;
//            this.username = username;
//            this.password = password;
//        }
////        public Employee(String firstName, String lastName) {
////            this.firstName = firstName;
////            this.lastName = lastName;
////        }
////
////        public Employee(String firstName, String lastName, String username, String password) {
////            this.firstName = firstName;
////            this.lastName = lastName;
////            this.username = username;
////            this.password = password;
////        }
//
////        public int getEmpID() {
////            return empID;
////        }
////
////        public void setEmpID(int empID) {
////            this.empID = empID;
////        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
//        }
//
//        public String getLastName() {
//            return lastName;
//        }
//
//        public void setLastName(String lastName) {
//            this.lastName = lastName;
//        }
//
//        public String getUsername() {
//            return username;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//
////        public String getReadBooks() {
////            List<BookModel.Book> books = FileService.read("books.json").getBooks();
////            String fmt = "";
////            for (int i = 0; i < readID.length; i++) {
////                if (readID[i] != 0)
////                    fmt += String.format("'%s' by %s. ", books.get(readID[i]).getBookName(), books.get(readID[i]).getAuthorName());
////            }
////            return fmt;
////        }
////
////        public String getReadNowBooks() {
////            List<BookModel.Book> books = FileService.read("books.json").getBooks();
////            String fmt = "";
////            for (int i = 0; i < readNowID.length; i++) {
////            if(readNowID[i] != 0)
////                fmt += String.format("'%s' by %s. ", books.get(readNowID[i]).getBookName(), books.get(readNowID[i]).getAuthorName());
////            }
////            return fmt;
////        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//    }
//}
