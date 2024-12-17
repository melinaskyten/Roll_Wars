import java.io.FileInputStream;
import java.util.Properties;

public class Rules {

    private static Rules instance;
    private String rules;

//     Singleton
//     Hidden constructor
//     Only way to create an instance of Rules is through
//     the static method getInstance()

    private Rules() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/Rules.properties"));
        } catch (Exception e) {
            System.out.println("File could not be loaded");
            e.printStackTrace();
        }

        setRules(properties.getProperty("rules"));
    }

    public static Rules getInstance() {
        if (instance == null) {
            instance = new Rules();
        }
        return instance;
    }

    public void printRules() {
        System.out.println(rules);
    }

    private void setRules(String rules) {
        this.rules = rules;
    }

}
