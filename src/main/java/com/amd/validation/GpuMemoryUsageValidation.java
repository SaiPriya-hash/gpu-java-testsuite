package com.amd.validation;

/**
 * GpuMemoryUsageValidation checks that GPU memory usage is within a safe threshold.
 * It ensures that usage remains below 90% of the total memory capacity.
 */
public class GpuMemoryUsageValidation implements ValidationTask {

    // SLF4J logger for runtime diagnostics
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(GpuMemoryUsageValidation.class);

    /**
     * Executes the memory usage validation.
     *
     * @param context the test context containing GPU telemetry
     * @return ValidationResult indicating pass/fail and a descriptive message
     */
    @Override
    public ValidationResult execute(TestContext context) {
        double used = context.getMemoryUsedGB();
        double total = context.getTotalMemoryGB();

        LOG.info("Validating GPU Memory Usage for {}", context.getDeviceId());
        LOG.info("Memory Used: {} GB / {} GB", used, total);

        // Edge case: if total memory is not properly detected
        if (total <= 0) {
            return new ValidationResult(
                "GpuMemoryUsageValidation",
                false,
                "Total memory reported as 0 GB"
            );
        }

        // Compute usage percentage
        double usagePercent = (used / total) * 100;

        // Define acceptable threshold for memory usage
        boolean isHealthy = usagePercent < 90;

        // Return the validation result
        return new ValidationResult(
            "GpuMemoryUsageValidation",
            isHealthy,
            String.format("Used %.1f%% of GPU memory", usagePercent)
        );
    }
}
