package com.amd.validation;

/**
 * Interface representing a single GPU validation task.
 * <p>
 * Each implementing class must define the logic to validate a GPU based on a given {@link TestContext}.
 * This abstraction allows the test framework to run multiple types of checks (e.g., PCIe link status,
 * fan speed validation, thermal monitoring) uniformly using polymorphism.
 * </p>
 */
public interface ValidationTask {

    /**
     * Executes the validation logic for the provided GPU context.
     *
     * @param context the {@link TestContext} containing metadata and real-time telemetry for the device
     * @return a {@link ValidationResult} indicating whether the validation passed or failed
     */
    ValidationResult execute(TestContext context);
}
