package com.amd.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class IntegrationFlowTest {

    @Test
    void fullFactoryFlow() {
        TestContext ctx = new TestContext("GPU-BETA-007");

        List<ValidationTask> tasks = List.of(
            new PCIeLinkValidation(),
            new ThermalMonitorCheck()  // add more as they’re created
        );

        TestReport report = new TestRunner(tasks).runAll(ctx);

        assertEquals(tasks.size(), report.total());
        assertEquals(0,             report.failed(), report.toString());
        System.out.println(report); // lets you eyeball the summary in CI logs
    }
}
