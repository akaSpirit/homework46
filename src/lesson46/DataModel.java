package lesson46;

import java.util.List;
import java.util.Map;

public class DataModel {
    private final List<Book> books;
    private final Map<String, Employee> employees;

    public DataModel() {
        books = FileService.readBooks();
        employees = FileService.readEmployees();
    }

    public List<Book> getBooks() {
        return books;
    }

    public Map<String, Employee> getEmployees() {
        return employees;
    }


    public static class Book {
        private int id;
        private String bookName;
        private String authorName;
        private BookState bookState;
        private String info;
        private String[] currentReaders;
        private String[] pastReaders;

        public String getBookName() {
            return bookName;
        }

        public String getAuthorName() {
            return authorName;
        }

        public BookState getBookState() {
            return bookState;
        }

        public String getInfo() {
            return info;
        }

        public int getId() {
            return id;
        }

        public String[] getCurrentReaders() {
            return currentReaders;
        }

        public String[] getPastReaders() {
            return pastReaders;
        }

        public String getCurrentReader() {
            Map<String, DataModel.Employee> employees = FileService.readEmployees();
            String fmt = "";
            if (bookState == BookState.NOT_AVAILABLE) {
                for (int i = 0; i < currentReaders.length; i++) {
                    if (employees.containsKey(currentReaders[i])) {
                        fmt = String.format("%s", employees.get(currentReaders[i]).email);
                    }

                }
            } else fmt = "Book is free";
            return fmt;
        }
    }

    public static class Employee {
        private String firstName;
        private String lastName;
        private String email;
        private String username;
        private String password;

        private int[] readNowID;
        private int[] readPastID;

        public Employee(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public Employee(String firstName, String lastName, String email, String username, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.username = username;
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int[] getReadNowID() { return readNowID; }

        public int[] getReadPastID() { return readPastID; }

        public void setReadNowID(int[] readNowID) {
            this.readNowID = readNowID;
        }

        public void setReadPastID(int[] readPastID) {
            this.readPastID = readPastID;
        }

        public String getReadNowBooks() {
            List<DataModel.Book> books = FileService.readBooks();
            String fmt = "";
            for (int i = 0; i < readNowID.length; i++) {
                fmt += String.format("'%s' by %s. ", books.get(readNowID[i]).bookName, books.get(readNowID[i]).authorName);
            }
            return fmt;
        }

        public String getReadPastBooks() {
            List<DataModel.Book> books = FileService.readBooks();
            String fmt = "";
            for (int i = 0; i < readPastID.length; i++) {
                fmt += String.format("'%s' by %s. ", books.get(readPastID[i]).bookName, books.get(readPastID[i]).authorName);
            }
            return fmt;
        }
    }
}

