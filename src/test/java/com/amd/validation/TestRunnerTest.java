package com.amd.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestRunnerTest {

    private TestContext context;

    @BeforeEach
    void setUp() {
        context = new TestContext("GPU-001");
    }

    @Test
    void testValidationFlow() {
        ValidationTask pcie = new PCIeLinkValidation();
        ValidationTask thermal = new ThermalMonitorCheck();

        TestRunner runner = new TestRunner(Arrays.asList(pcie, thermal));
        runner.runAll(context);
    }

    @Test
    void testWithEmptyTaskList() {
        TestRunner runner = new TestRunner(Collections.emptyList());

        assertDoesNotThrow(() -> runner.runAll(context));
    }

    @Test
    void testTaskThatThrowsException() {
        ValidationTask faultyTask = new ValidationTask() {
            @Override
            public ValidationResult execute(TestContext context) {
                throw new RuntimeException("Simulated failure");
            }
        };

        TestRunner runner = new TestRunner(List.of(faultyTask));

        assertThrows(RuntimeException.class, () -> runner.runAll(context));
    }

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
