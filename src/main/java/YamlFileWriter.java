import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Service
public class YamlFileWriter {

    public void writeToYamlFile(Map<String, Object> properties, String fileName) throws IOException {
        Representer representer = new Representer();
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(representer, options);

        try (FileWriter writer = new FileWriter(fileName)) {
            yaml.dump(properties, writer);
        }
    }
}
