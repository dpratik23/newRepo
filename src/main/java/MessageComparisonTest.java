import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class MessageComparisonTest {

    static class MessagePair {
        String inputMessage;
        String outputMessage;

        MessagePair(String inputMessage, String outputMessage) {
            this.inputMessage = inputMessage;
            this.outputMessage = outputMessage;
        }
    }

    static List<MessagePair> messagePairs() throws Exception {
        Path inputDir = Paths.get("src/test/resources/inputs");
        Path outputDir = Paths.get("src/test/resources/outputs");

        List<Path> inputFiles = Files.list(inputDir).sorted().collect(Collectors.toList());
        List<Path> outputFiles = Files.list(outputDir).sorted().collect(Collectors.toList());

        assertTrue(inputFiles.size() == outputFiles.size(), "Mismatch in number of input and output files");

        return inputFiles.stream().map(inputPath -> {
            try {
                String inputMessage = Files.readString(inputPath);
                String outputMessage = Files.readString(outputDir.resolve(inputPath.getFileName()));
                return new MessagePair(inputMessage, outputMessage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    @ParameterizedTest
    @MethodSource("messagePairs")
    void testMessages(MessagePair messagePair) {
        assertEquals(messagePair.inputMessage, messagePair.outputMessage, "Messages do not match");
    }
}
