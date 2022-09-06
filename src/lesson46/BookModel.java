package lesson46;

import java.beans.Transient;
import java.util.List;

//public class BookModel {
////    private List<Book> books = FileService.read("books2.json").getBooks();
////    private List<EmployeeModel.Employee> employees = FileService.read("employees2.json").getEmployees();
////    public List<Book> getBooks() {
////        return books;
////    }
////    public void setBooks(List<Book> books) {
////        this.books = books;
////    }
//
//    public class Book {
////        private int id;
//        private String bookName;
//        private String authorName;
//        private BookState bookState;
//        private String info;
////        private int employeeID;
//
////        public int getEmployeeID() {
////            return employeeID;
////        }
////
////        public void setEmployeeID(int employeeID) {
////            this.employeeID = employeeID;
////        }
//
////        public Book(int id, String bookName, String authorName, String info) {
////            this.id = id;
////            this.bookName = bookName;
////            this.authorName = authorName;
////            this.info = info;
////        }
//
//        public Book(String bookName, String authorName, String info) {
//            this.bookName = bookName;
//            this.authorName = authorName;
//            this.info = info;
//        }
//
////        public int getId() {
////            return id;
////        }
////
////        public void setId(int id) {
////            this.id = id;
////        }
//
//        public String getBookName() {
//            return bookName;
//        }
//
//        public String getAuthorName() {
//            return authorName;
//        }
//
//        public void setAuthorName(String authorName) {
//            this.authorName = authorName;
//        }
//
//        public BookState getBookState() {
//            return bookState;
//        }
//        public String getInfo() {
//            return info;
//        }
//
////        public String getReader() {
////            List<EmployeeModel.Employee> employees = FileService.read("employees.json").getEmployees();
////            String fmt = "";
////            if(bookState == BookState.NOT_AVAILABLE) {
////                fmt = String.format("%s %s", employees.get(employeeID).getFirstName(), employees.get(employeeID).getLastName());
////            } else fmt = "Book is free";
////            return fmt;
////        }
//    }
//}
