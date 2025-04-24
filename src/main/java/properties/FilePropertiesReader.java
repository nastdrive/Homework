package properties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static jdk.jfr.internal.SecuritySupport.newFileInputStream;

public class FilePropertiesReader implements IPropertyReader {

    @Override
    public Map<String, String> getSettings() throws IOException {
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Path.of(System.getProperty("user.dir") + "/src/main/resources/db.properties")));

        Map<String,String> settings = new HashMap<>();
        for (String key: properties.stringPropertyNames()) {
            settings.put(key, properties.getProperty(key));
        }
        return settings;
    }
}
