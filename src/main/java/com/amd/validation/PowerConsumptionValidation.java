package com.amd.validation;

/**
 * PowerConsumptionValidation checks whether the GPU's power draw
 * is within acceptable safe operational limits.
 */
public class PowerConsumptionValidation implements ValidationTask {

    // Logger for structured output
    private static final org.slf4j.Logger LOG =
            org.slf4j.LoggerFactory.getLogger(PowerConsumptionValidation.class);

    // Power threshold in watts beyond which GPU is considered unhealthy
    private static final double MAX_SAFE_WATTS = 200.0;

    /**
     * Executes power consumption validation.
     *
     * @param context The GPU device context containing power telemetry
     * @return ValidationResult indicating if power draw is within limits
     */
    @Override
    public ValidationResult execute(TestContext context) {
        double watts = context.getPowerWatts();

        // Log the power usage for traceability
        LOG.info("Validating Power Consumption for {}: {}W",
                 context.getDeviceId(), watts);

        // Evaluate health based on threshold
        boolean isHealthy = watts <= MAX_SAFE_WATTS;

        // Return structured result
        return new ValidationResult(
            "PowerConsumptionValidation",
            isHealthy,
            String.format("GPU Power Usage: %.1fW", watts)
        );
    }
}
