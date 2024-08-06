import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Collections;

public class TestRunner {
    public static boolean runTests(File pomFile) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(pomFile);
        request.setGoals(Collections.singletonList("test"));

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/path/to/maven"));

        try {
            InvocationResult result = invoker.execute(request);
            return result.getExitCode() == 0;
        } catch (MavenInvocationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
