@Service
public class TestProfileYamlGenerator {

    @Autowired
    private ConfigService configService;

    @Autowired
    private YamlFileWriter yamlFileWriter;

    public void generateTestProfileYaml() throws IOException {
        String profile = "test";
        Map<String, Object> properties = configService.fetchConfigProperties(profile);
        String fileName = "src/main/resources/application-test.yml";
        yamlFileWriter.writeToYamlFile(properties, fileName);
    }
}
