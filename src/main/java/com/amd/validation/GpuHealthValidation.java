package com.amd.validation;

/**
 * GpuHealthValidation performs a combined check on the GPU's temperature and memory usage.
 * It validates whether both metrics are within specified safety thresholds.
 */
public class GpuHealthValidation implements ValidationTask {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(GpuHealthValidation.class);

    private final double maxTemp;
    private final double maxMem;

    /**
     * Constructs a GpuHealthValidation task with given max thresholds.
     *
     * @param maxTemp Maximum allowed GPU temperature in °C
     * @param maxMem  Maximum allowed GPU memory usage in GB
     */
    public GpuHealthValidation(double maxTemp, double maxMem) {
        this.maxTemp = maxTemp;
        this.maxMem = maxMem;
    }

    /**
     * Executes the validation by checking temperature and memory usage.
     *
     * @param context TestContext containing the current GPU state
     * @return ValidationResult indicating pass/fail with a descriptive message
     */
    @Override
    public ValidationResult execute(TestContext context) {
        double temp = context.getGpuTemperature();
        double mem = context.getMemoryUsedGB();

        // Log the current sensor readings
        LOG.info("Validating GPU Health for {}: Temp={}°C, Mem Used={}GB",
                 context.getDeviceId(), temp, mem);

        // Determine if both metrics are within acceptable limits
        boolean pass = temp <= maxTemp && mem <= maxMem;

        // Construct a readable validation message
        String msg = String.format("Temp: %.1f°C, Mem: %.1fGB", temp, mem);

        return new ValidationResult("GpuHealthValidation", pass, msg);
    }
}
