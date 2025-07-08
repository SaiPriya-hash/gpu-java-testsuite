package com.amd.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FanSpeedValidation checks whether the GPU fan speed is operating within
 * a safe and expected range.
 * 
 * Acceptable range: 800 - 3500 RPM.
 * 
 * This validation helps detect:
 * - Overcooling or undercooling issues
 * - Hardware failures in fan mechanism
 * - Firmware misconfigurations affecting fan control
 */
public class FanSpeedValidation implements ValidationTask {

    private static final Logger LOG = LoggerFactory.getLogger(FanSpeedValidation.class);

    /**
     * Executes the fan speed validation task.
     *
     * @param context The current GPU device context, including sensor readings
     * @return ValidationResult indicating pass/fail and a short description
     */
    @Override
    public ValidationResult execute(TestContext context) {
        int fanRpm = context.getFanSpeedRPM();
        
        // Log current fan speed for traceability
        LOG.info("Validating Fan Speed for {}: {} RPM", context.getDeviceId(), fanRpm);

        // Define valid RPM range (device-specific tuning can replace these values)
        boolean isHealthy = fanRpm >= 800 && fanRpm <= 3500;

        // Return result with outcome and explanation
        return new ValidationResult(
            "FanSpeedValidation",
            isHealthy,
            isHealthy ? "Fan speed normal" : "Fan speed out of range"
        );
    }
}
