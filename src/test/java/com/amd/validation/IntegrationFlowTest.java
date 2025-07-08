package com.amd.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the full GPU factory validation pipeline.
 *
 * <p>This test simulates running the full stack of validation tasks on a dummy GPU context
 * to ensure that the entire test suite works together end-to-end.</p>
 */
class IntegrationFlowTest {

    /**
     * Simulates the complete factory test flow using all implemented validation tasks.
     *
     * <p>This test validates that:
     * <ul>
     *   <li>All tasks are executed.</li>
     *   <li>No task fails (under mock/dummy context).</li>
     * </ul>
     * </p>
     */
    @Test
    void fullFactoryFlow() {
        // Create a simulated GPU test context with a mock device ID
        TestContext ctx = new TestContext("GPU-BETA-42");

        // Populate the context with dummy metadata (PCIe location, memory usage, etc.)
        SystemInfoFetcher.populate(ctx);

        // Define the full set of validation tasks to run
        List<ValidationTask> tasks = List.of(
            new PCIeLinkValidation(),
            new ThermalMonitorCheck(),
            new GpuMemoryUsageValidation(),
            new FanSpeedValidation(),
            new PowerConsumptionValidation()
        );

        // Run all tasks and collect the report
        TestReport report = new TestRunner(tasks).runAll(ctx);

        // Validate that all tasks executed
        assertEquals(tasks.size(), report.total(), "All tasks should execute");

        // Validate that all tasks passed successfully
        assertEquals(0, report.failed(), report.toString());
    }
}
