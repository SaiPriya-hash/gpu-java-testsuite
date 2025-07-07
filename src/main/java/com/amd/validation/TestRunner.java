package com.amd.validation;

import java.util.List;

public class TestRunner {
    private final List<ValidationTask> tasks;

    public TestRunner(List<ValidationTask> tasks) { this.tasks = tasks; }

    public TestReport runAll(TestContext context) {
        TestReport report = new TestReport();
        for (ValidationTask t : tasks) {
            report.add(t.execute(context));
        }
        return report;
    }
}
