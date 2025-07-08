package com.amd.validation;

/**
 * Simulates a thermal monitoring validation step for a GPU device.
 * <p>
 * This task is typically used to ensure that the GPU temperature is within safe thresholds.
 * In this POC version, it simulates a "pass" scenario.
 * </p>
 */
public class ThermalMonitorCheck implements ValidationTask {

    /** SLF4J logger for diagnostic output */
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ThermalMonitorCheck.class);

    /** Flag indicating whether to suppress detailed logs (useful in summary-only reports) */
    private final boolean summaryOnly;

    /**
     * Default constructor. Enables full output (summaryOnly = false).
     */
    public ThermalMonitorCheck() {
        this(false);
    }

    /**
     * Constructor that allows controlling logging verbosity.
     *
     * @param summaryOnly if true, suppresses verbose logs
     */
    public ThermalMonitorCheck(boolean summaryOnly) {
        this.summaryOnly = summaryOnly;
    }

    /**
     * Executes the thermal validation logic for the given GPU.
     *
     * @param context the test context containing GPU metadata and readings
     * @return validation result (always passing in this simulation)
     */
    @Override
    public ValidationResult execute(TestContext context) {
        if (!summaryOnly) {
            LOG.info("Running thermal monitor check on {}", context.getDeviceId());
        }

        // TODO: Replace with real thermal check (e.g., context.getGpuTemperature())
        return new ValidationResult("ThermalMonitorCheck", true, "Thermals OK");
    }
}
