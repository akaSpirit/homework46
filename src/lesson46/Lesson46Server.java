package lesson46;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import server.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Lesson46Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();
    private final Map<String, Boolean> isLogin = new HashMap<>();
    private final DataModel dm = new DataModel();
    private DataModel.Employee enteredEmployee;

    private final IdGenerator idGenerator = new IdGenerator();
    private String id;
    private Map<Cookie, DataModel.Employee> cookieEmployeeMap = new HashMap<>();

    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);
        isLogin.put("status", true);

        registerGet("/books", this::bookHandler);
        registerGet("/info", this::infoHandler);
        registerGet("/employees", this::employeesHandler);
        registerGet("/employee", this::employeeHandler);

        registerGet("/register", this::registerFormGet);
        registerPost("/register", this::registerFormPost);

        registerGet("/login", e -> {
            renderTemplate(e, "login.html", isLogin);
        });
        registerPost("/login", this::loginPost);

        registerGet("/profile", this::profileGet);
        registerGet("/cookie", this::cookieHandler);
    }

    private void bookHandler(HttpExchange exchange) {
        renderTemplate(exchange, "books.html", new DataModel());
    }

    private void infoHandler(HttpExchange exchange) {
        renderTemplate(exchange, "info.html", new DataModel());
    }

    public void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> map = Utils.parseUrlEncoded(raw, "&");
        String email = map.get("email");
        String password = map.get("password");

        if (dm.getEmployees().containsKey(email)) {
            if (dm.getEmployees().get(email).getPassword().equals(password)) {
                enteredEmployee = dm.getEmployees().get(email);

                id = idGenerator.makeCode("User" + enteredEmployee.getEmail());


                Cookie sessionCookieLogin = Cookie.make(email, id);
                sessionCookieLogin.setMaxAge(600);
                sessionCookieLogin.setHttpOnly(true);
                exchange.getResponseHeaders().add("Set-Cookie", sessionCookieLogin.toString());
                Map<String, String> cookies = Cookie.parse(sessionCookieLogin.toString());
                setCookie(exchange, sessionCookieLogin);
                cookieEmployeeMap.put(sessionCookieLogin, enteredEmployee);

                redirect303(exchange, "/profile");
            } else {
                enteredEmployee = null;
                isLogin.put("status", false);
                redirect303(exchange, "/login");
            }
        } else {
            enteredEmployee = null;
            isLogin.put("status", false);
            redirect303(exchange, "/login");
        }
    }

    private void employeesHandler(HttpExchange exchange) {
        renderTemplate(exchange, "employees.html", new DataModel());
    }

    private void employeeHandler(HttpExchange exchange) {
        renderTemplate(exchange, "employee.html", enteredEmployee);
    }

    public void registerFormGet(HttpExchange exchange) {
        Path path = makeFilePath("register.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    public void registerFormPost(HttpExchange exchange) {
        String raw = getBody(exchange);

        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");

        String email = parsed.get("email");
        String firstname = parsed.get("firstname");
        String lastname = parsed.get("lastname");
        String username = parsed.get("username");
        String password = parsed.get("password");

        if (dm.getEmployees().containsKey(email)) {
            redirect303(exchange, "/register");
            return;
        }
        dm.getEmployees().put(email, new DataModel.Employee(firstname, lastname, email, username, password));
        FileService.writeEmployees(dm.getEmployees());
        redirect303(exchange, "/login");
    }

    public void profileGet(HttpExchange exchange) {
        renderTemplate(exchange, "profile.html", enteredEmployee);
    }

//    public void profilePost(HttpExchange exchange) {
//        List<DataModel.Book> books = FileService.readBooks();
//        DataModel.Book deleteBook = books.get(books.size() -1);
//        books.remove(deleteBook);
//
//        redirect303(exchange, "/books");
//    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("data"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            Template temp = freemarker.getTemplate(templateFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {
                temp.process(dataModel, writer);
                writer.flush();
                var data = stream.toByteArray();
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
    private void cookieHandler(HttpExchange exchange) {
        Cookie sessionCookie = Cookie.make("userId", "123");
        exchange.getResponseHeaders().add("Set-Cookie", sessionCookie.toString());

        Map<String, Object> data = new HashMap<>();
        String name = "times";

        Cookie c1 = Cookie.make("user%Id", "456");
        setCookie(exchange, c1);

        Cookie c2 = Cookie.make("user-mail", "example@mail");
        setCookie(exchange, c2);

        Cookie c3 = Cookie.make("restricted()<>@,;:\\\"/[]?={}", "()<>@,;:\\\"/[]?={}");
        setCookie(exchange, c3);

        String cookieString = getCookie(exchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        String cookieValue = cookies.getOrDefault(name, "0");
        int times = Integer.parseInt(cookieValue) + 1;

        Cookie c4 = new Cookie(name, times);
        setCookie(exchange, c4);
        data.put(name, times);
        data.put("cookies", cookies);

        renderTemplate(exchange, "cookie.html", data);
    }

}
