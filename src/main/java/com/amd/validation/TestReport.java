package com.amd.validation;

import java.util.ArrayList;
import java.util.List;

public class TestReport {
    private final List<ValidationResult> results = new ArrayList<>();

    public void add(ValidationResult result) {
        results.add(result);
    }

    public long passed() {
        return results.stream().filter(ValidationResult::isPassed).count();
    }

    public long failed() {
        return results.size() - passed();
    }

    public int total() {
        return results.size();
    }

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
