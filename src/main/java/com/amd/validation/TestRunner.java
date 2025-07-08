package com.amd.validation;

import java.util.List;

/**
 * The TestRunner class executes a series of validation tasks against a specific GPU device.
 * <p>
 * Each task implements {@link ValidationTask} and is run in order. Results are collected
 * into a {@link TestReport}, which includes pass/fail status for each task as well as
 * the full {@link TestContext} (device metadata).
 * </p>
 */
public class TestRunner {

    /** List of validation tasks to run (e.g., PCIe check, thermal check, etc.) */
    private final List<ValidationTask> tasks;

    /**
     * Constructs a TestRunner with a provided list of tasks.
     *
     * @param tasks the sequence of validation tasks to execute
     */
    public TestRunner(List<ValidationTask> tasks) {
        this.tasks = tasks;
    }

    /**
     * Runs all validation tasks using the provided GPU device context.
     *
     * @param context the metadata and runtime info of the GPU under test
     * @return a {@link TestReport} summarizing results of all tasks
     */
    public TestReport runAll(TestContext context) {
        TestReport report = new TestReport();

        // Execute each validation task and collect its result
        for (ValidationTask t : tasks) {
            report.add(t.execute(context));
        }

        // Attach device metadata (e.g., ID, memory, PCI info) to the report
        report.setContext(context);

        return report;
    }
}
