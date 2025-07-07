package com.amd.validation;

import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Starting GPU Factory Validation Framework...");

        TestContext context = new TestContext("GPU-001");

        ValidationTask pcie = new PCIeLinkValidation();
        ValidationTask thermal = new ThermalMonitorCheck();

        TestRunner runner = new TestRunner(Arrays.asList(pcie, thermal));
        runner.runAll(context);
        
        TestReport report = runner.runAll(context);
        System.out.println(report);

        System.out.println("Validation complete.");
    }
}
