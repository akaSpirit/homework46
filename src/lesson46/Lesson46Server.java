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
    private final Map<String, Boolean> boolLogin = new HashMap<>();
    String id;
    String email;

    public String getEmail() {
        return email;
    }

    String password;
    String username;
    String firstname;
    String lastname;
    EmployeeModel.Employee emp;

    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);
        boolLogin.put("status", true);
        registerGet("/books", this::bookHandler);
        registerGet("/info", this::infoHandler);
        registerGet("/employees", this::employeesHandler);
        registerGet("/employee", this::employeeHandler);

        registerGet("/cookie", this::cookieHandler);
        registerGet("/login", e -> {
            renderTemplate(e, "login.html", boolLogin);
        });
//        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/register", this::registerFormGet);
        registerPost("/register", this::registerFormPost);
        registerGet("/profile", this::profileGet);
    }

    public EmployeeModel.Employee getEmp() {
        return emp;
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

//    public void loginGet(HttpExchange exchange) {
//        Path path = makeFilePath("login.html");
//        sendFile(exchange, path, ContentType.TEXT_HTML);
//    }

    public void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> map = Utils.parseUrlEncoded(raw, "&");
        email = map.get("email");
        password = map.get("password");

        var users = UserFileService.readJson();
        emp = new EmployeeModel.Employee(users.size(), firstname, lastname, username, password);

        if (users.containsKey(email)) {
            if (users.get(email).getPassword().equals(password)) {
//                profileGetData(exchange);
                redirect303(exchange, "/profile");
//                empl = new EmployeeModel.Employee(users.size() + 1, firstname, lastname, username, password);
            }
        }else {
//                empl = null;
                boolLogin.put("status", false);
                redirect303(exchange, "/login");
            }
        }

        public void registerFormGet (HttpExchange exchange){
            Path path = makeFilePath("register.html");
            sendFile(exchange, path, ContentType.TEXT_HTML);
        }

        public void registerFormPost (HttpExchange exchange){
            String raw = getBody(exchange);

            var users = UserFileService.readJson();

            Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");

            email = parsed.get("email");
            password = parsed.get("password");
            username = parsed.get("username");
            firstname = parsed.get("firstname");
            lastname = parsed.get("lastname");

            if (users.containsKey(email)) {
                String str = "Данный email уже используется";
                try {
                    sendByteData(exchange, ResponseCodes.ALREADY_EXISTS, ContentType.TEXT_PLAIN, str.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            users.put(email, new EmployeeModel.Employee(users.size() + 1, firstname, lastname, username, password));
            id = String.format("%s", users.size() + 1);
            UserFileService.writeJson(users);

            redirect303(exchange, "/login");
        }

        public void profileGetData (HttpExchange exchange){
            renderTemplate(exchange, "profile.html", new EmployeeModel());
        }

        public void profileGet (HttpExchange exchange){
            Path path = makeFilePath("emptyprofile.html"); //emptyprofile
            sendFile(exchange, path, ContentType.TEXT_HTML);
        }


        private static Configuration initFreeMarker () {
            try {
                Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
                // путь к каталогу в котором у нас хранятся шаблоны
                // это может быть совершенно другой путь, чем тот, откуда сервер берёт файлы
                // которые отправляет пользователю
                cfg.setDirectoryForTemplateLoading(new File("data"));

                // прочие стандартные настройки о них читать тут
                // https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
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

        private void bookHandler (HttpExchange exchange){
            renderTemplate(exchange, "books.html", new BookModel());
        }
        private void infoHandler (HttpExchange exchange){
            renderTemplate(exchange, "info.html", new BookModel());
        }
        private void employeesHandler (HttpExchange exchange){
            renderTemplate(exchange, "employees.html", new EmployeeModel());
        }
        private void employeeHandler (HttpExchange exchange){
            renderTemplate(exchange, "employee.html", new EmployeeModel());
        }

        protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel){
            try {
                // загружаем шаблон из файла по имени.
                // шаблон должен находится по пути, указанном в конфигурации
                Template temp = freemarker.getTemplate(templateFile);

                // freemarker записывает преобразованный шаблон в объект класса writer
                // а наш сервер отправляет клиенту массивы байт
                // по этому нам надо сделать "мост" между этими двумя системами

                // создаём поток который сохраняет всё, что в него будет записано в байтовый массив
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // создаём объект, который умеет писать в поток и который подходит для freemarker
                try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

                    // обрабатываем шаблон заполняя его данными из модели
                    // и записываем результат в объект "записи"
                    temp.process(dataModel, writer);
                    writer.flush();

                    // получаем байтовый поток
                    var data = stream.toByteArray();

                    // отправляем результат клиенту
                    sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
                }
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        }

    }
