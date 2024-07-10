import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SpringBootTestClassGenerator {

    private static final String SOURCE_DIR = "src/main/java";
    private static final String TEST_DIR = "src/test/java";

    public static void main(String[] args) {
        try {
            // Find the Spring Boot application class
            File springBootAppClass = findSpringBootApplicationClass(new File(SOURCE_DIR));

            if (springBootAppClass == null) {
                System.out.println("Spring Boot application class not found.");
                return;
            }

            // Extract the package name and class name
            String packageName = extractPackageName(springBootAppClass);
            String className = springBootAppClass.getName().replace(".java", "");

            // Generate the test class
            generateTestClass(packageName, className);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File findSpringBootApplicationClass(File dir) throws IOException {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                File result = findSpringBootApplicationClass(file);
                if (result != null) {
                    return result;
                }
            }
        } else if (dir.isFile() && dir.getName().endsWith(".java")) {
            List<String> lines = Files.readAllLines(dir.toPath());
            for (String line : lines) {
                if (line.contains("@SpringBootApplication")) {
                    return dir;
                }
            }
        }
        return null;
    }

    private static String extractPackageName(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        for (String line : lines) {
            if (line.startsWith("package ")) {
                return line.replace("package ", "").replace(";", "").trim();
            }
        }
        return "";
    }

    private static void generateTestClass(String packageName, String className) throws IOException {
        String testPackagePath = packageName.replace('.', '/');
        Path testDirPath = Paths.get(TEST_DIR, testPackagePath);
        Files.createDirectories(testDirPath);

        String testClassName = className + "Test";
        File testFile = new File(testDirPath.toFile(), testClassName + ".java");

        if (!testFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
                writer.write("package " + packageName + ";\n\n");
                writer.write("import org.junit.jupiter.api.Test;\n");
                writer.write("import org.springframework.boot.test.context.SpringBootTest;\n\n");
                writer.write("@SpringBootTest\n");
                writer.write("public class " + testClassName + " {\n\n");
                writer.write("    @Test\n");
                writer.write("    public void contextLoads() {\n");
                writer.write("        // Add your test logic here\n");
                writer.write("    }\n");
                writer.write("}\n");
            }
            System.out.println("Created test class: " + testFile.getPath());
        } else {
            System.out.println("Test class already exists: " + testFile.getPath());
        }
    }
}
