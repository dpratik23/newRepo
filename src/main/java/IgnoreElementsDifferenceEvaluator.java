import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;

public class IgnoreElementsDifferenceEvaluator implements DifferenceEvaluator {
    private final Set<String> ignoredElements;

    public IgnoreElementsDifferenceEvaluator(Set<String> ignoredElements) {
        this.ignoredElements = ignoredElements;
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.EQUAL) {
            return outcome;
        }
        if (comparison.getControlDetails().getTarget().getNodeName() != null &&
            ignoredElements.contains(comparison.getControlDetails().getTarget().getNodeName())) {
            return ComparisonResult.SIMILAR;
        }
        return outcome;
    }
}
