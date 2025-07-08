package com.amd.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * TestReport encapsulates the full result of a GPU validation run.
 * <p>
 * It holds a collection of {@link ValidationResult} objects representing individual
 * validation task outcomes, along with the {@link TestContext} (device metadata) that
 * was used during execution.
 * </p>
 * 
 * This class is used to:
 * <ul>
 *   <li>Aggregate task results</li>
 *   <li>Provide summary statistics (pass/fail/total)</li>
 *   <li>Export or print results via {@code toString()}</li>
 * </ul>
 */
public class TestReport {

    /** List of results from each validation task */
    private final List<ValidationResult> results = new ArrayList<>();

    /** Metadata and runtime context of the GPU device (not serialized by default) */
    private transient TestContext context;

    /**
     * Adds a validation result to the report.
     *
     * @param result the result of a validation task
     */
    public void add(ValidationResult result) {
        results.add(result);
    }

    /**
     * @return the number of validation tasks that passed
     */
    public long passed() {
        return results.stream().filter(ValidationResult::isPassed).count();
    }

    /**
     * @return the number of validation tasks that failed
     */
    public long failed() {
        return results.size() - passed();
    }

    /**
     * @return the total number of validation tasks executed
     */
    public int total() {
        return results.size();
    }

    /**
     * @return the list of all validation results
     */
    public List<ValidationResult> getResults() {
        return results;
    }

    /**
     * @return the device metadata context associated with this report
     */
    public TestContext getContext() {
        return context;
    }

    /**
     * Associates a {@link TestContext} with the report.
     *
     * @param context metadata and telemetry used for the validation run
     */
    public void setContext(TestContext context) {
        this.context = context;
    }

    /**
     * Returns a human-readable summary of all validation task results.
     * Includes per-task status and overall pass/fail counts.
     *
     * @return formatted multi-line test summary
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n=== Test Summary ===\n");
        for (ValidationResult r : results) {
            sb.append(String.format("• %-25s : %s (%s)%n",
                r.getTaskName(),
                r.isPassed() ? "PASS" : "FAIL",
                r.getMessage()));
        }
        sb.append(String.format("Total: %d  Passed: %d  Failed: %d%n", total(), passed(), failed()));
        return sb.toString();
    }
}
