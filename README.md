Скачайте тестируюущее приложение (HW06.zip) по [ссылке](https://stepik.org/media/attachments/lesson/13016/HW06.zip).

# Задача
Добавить в сервер класс resources.TestResource

```Java
public class TestResource {
private String name;
private int age;

    public TestResource(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public TestResource() {
        this.name = "";
        this.age = 0;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

Написать ResourceServer, который будет содержать ссылку на TestResource.
Вывести ResourceServer в JMX с именем:
Admin:type=ResourceServerController
Сделать переменные "name" и "age" доступными для чтения из jmx клиента.

Написать сервлет, который будет обрабатывать запросы на /resources
При получении POST запроса с параметром path=path_to_resource
прочитает ресурс TestResource из указанного в параметре файла и сохранит ссылку в ResourceService

После чтения, значения name и age должны быть доступны по JMX.

Инструкция подготовки к локальной проверке:
Соберите сервер со всеми зависимостями на библиотеки в server.jar
Для этого запустите Maven projects/<Project name>/Plugins/assembly/assembly:single
либо assembly.sh (assembly.bat)

Скопируйте server.jar на уровень src и запустите
java -jar server.jar

В логах консоли вы должны увидеть сообщения о старте сервера.
Проверьте, что сервер отвечает на запросы браузера.

Инструкция подготовки к автоматической проверке:
Добавьте в лог сообщение "Server started". По появлению в логе этого сообщения тестирующая система пойдет, что к вашему серверу можно обращаться.
Соберите server.jar, содержащий все библиотеки.

Во время проверки тестовая система:
запустит ваш сервер,
подождет пока "Server started",
Создаст файл resource.xml в локальной файловой системе,
Пришлет по POST запросу имя этого файла в сервер,
проверит через JMX, что значение name и age совпадают с записанными