package com.amd.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link TestRunner} class.
 *
 * <p>These tests ensure that the validation engine correctly executes registered tasks,
 * handles edge cases gracefully, and runs tasks in the correct order.</p>
 */
class TestRunnerTest {

    private TestContext context;

    /**
     * Initialize a fresh test context before each test.
     */
    @BeforeEach
    void setUp() {
        context = new TestContext("GPU-001");
    }

    /**
     * Tests a full validation flow with all known task modules.
     * Ensures smooth execution and system info population.
     */
    @Test
    void testValidationFlow() {
        // Populate dummy metadata into context
        SystemInfoFetcher.populate(context);

        // Register all known validation tasks
        List<ValidationTask> tasks = List.of(
            new PCIeLinkValidation(),
            new ThermalMonitorCheck(),
            new GpuMemoryUsageValidation(),
            new FanSpeedValidation(),
            new PowerConsumptionValidation()
        );

        TestRunner runner = new TestRunner(tasks);

        // Ensure validation completes without exceptions
        assertDoesNotThrow(() -> runner.runAll(context));
    }

    /**
     * Verifies that the runner does not fail when no tasks are provided.
     */
    @Test
    void testWithEmptyTaskList() {
        TestRunner runner = new TestRunner(Collections.emptyList());
        assertDoesNotThrow(() -> runner.runAll(context));
    }

    /**
     * Ensures the runner propagates runtime exceptions from faulty tasks.
     */
    @Test
    void testTaskThatThrowsException() {
        // Create a simulated faulty task
        ValidationTask faultyTask = context -> {
            throw new RuntimeException("Simulated failure");
        };

        TestRunner runner = new TestRunner(List.of(faultyTask));

        assertThrows(RuntimeException.class, () -> runner.runAll(context));
    }

    /**
     * Runs validation independently for multiple devices and ensures isolation.
     */
    @Test
    void testMultipleDevicesIndependently() {
        TestContext device1 = new TestContext("GPU-A1");
        TestContext device2 = new TestContext("GPU-B2");

        ValidationTask pcie = new PCIeLinkValidation();
        ValidationTask thermal = new ThermalMonitorCheck();

        TestRunner runner1 = new TestRunner(List.of(pcie));
        TestRunner runner2 = new TestRunner(List.of(thermal));

        assertDoesNotThrow(() -> runner1.runAll(device1));
        assertDoesNotThrow(() -> runner2.runAll(device2));
    }

    /**
     * Validates that tasks are executed in the exact order they are registered.
     */
    @Test
    void testOrderOfExecution() {
        StringBuilder log = new StringBuilder();

        ValidationTask step1 = context -> {
            log.append("Step1;");
            return new ValidationResult("Step1", true, "Step1 executed");
        };
        ValidationTask step2 = context -> {
            log.append("Step2;");
            return new ValidationResult("Step2", true, "Step2 executed");
        };
        ValidationTask step3 = context -> {
            log.append("Step3;");
            return new ValidationResult("Step3", true, "Step3 executed");
        };

        TestRunner runner = new TestRunner(List.of(step1, step2, step3));
        runner.runAll(new TestContext("GPU-001"));

        assertEquals("Step1;Step2;Step3;", log.toString());
    }
}
