import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        //Загружаем файл из папки src/test/resources
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Ошибка: Не удалось найти файл config.properties в папке resources!");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            System.err.println("Ошибка при чтении файла конфигурации:");
            ex.printStackTrace();
        }
    }

    //Метод для получения любого значения по ключу
    public static String get(String key) {
        return properties.getProperty(key);
    }

    //Метод для вывода параметров в консоль с маскировкой пароля
    public static void printConfig() {
        System.out.println("==============================================");
        System.out.println("            КОНФИГУРАЦИЯ СТЕНДА               ");
        System.out.println("==============================================");

        properties.forEach((key, value) -> {
            String stringKey = (String) key;

            //Если в ключе есть слова password, pass или credentials — скрываем значение
            if (stringKey.toLowerCase().contains("password") || stringKey.toLowerCase().contains("cred")) {
                System.out.println(stringKey + ": ********");
            } else {
                System.out.println(stringKey + ": " + value);
            }
        });

        System.out.println("==============================================\n");
    }
}
