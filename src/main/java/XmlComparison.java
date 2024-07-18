import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.DifferenceEvaluators;

import java.util.Set;

public class XmlComparison {

    public static void main(String[] args) {
        String controlXml = "<root><a>1</a><b>2</b><c>3</c></root>";
        String testXml = "<root><a>1</a><b>20</b><c>3</c></root>";

        Set<String> ignoredFields = Set.of("b");

        IgnoreElementsDifferenceEvaluator ignoreElementsDifferenceEvaluator = new IgnoreElementsDifferenceEvaluator(ignoredFields);

        Diff diff = DiffBuilder.compare(controlXml)
                .withTest(testXml)
                .withDifferenceEvaluator(DifferenceEvaluators.chain(
                        DifferenceEvaluators.Default,
                        ignoreElementsDifferenceEvaluator))
                .checkForSimilar()
                .build();

        System.out.println("Are the XMLs similar? " + !diff.hasDifferences());
    }
}
