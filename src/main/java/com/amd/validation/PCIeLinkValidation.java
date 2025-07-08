package com.amd.validation;

/**
 * Validates the PCIe link training status of a GPU device.
 *
 * This class simulates checking whether the PCIe interface between
 * the host and GPU is correctly trained and operational.
 */
public class PCIeLinkValidation implements ValidationTask {

    // Logger instance using SLF4J
    private static final org.slf4j.Logger LOG =
            org.slf4j.LoggerFactory.getLogger(PCIeLinkValidation.class);

    // Controls whether detailed logs are shown
    private final boolean summaryOnly;

    /**
     * Default constructor with detailed logging enabled.
     */
    public PCIeLinkValidation() {
        this(false);
    }

    /**
     * Constructor allowing caller to enable/disable detailed logging.
     *
     * @param summaryOnly If true, skips detailed log output
     */
    public PCIeLinkValidation(boolean summaryOnly) {
        this.summaryOnly = summaryOnly;
    }

    /**
     * Executes the PCIe link validation.
     *
     * @param context Metadata and runtime information about the GPU
     * @return ValidationResult indicating success/failure
     */
    @Override
    public ValidationResult execute(TestContext context) {
        if (!summaryOnly) {
            LOG.info("Performing PCIe link training on {}", context.getDeviceId());
        }

        // Simulate successful PCIe link validation
        boolean isLinkTrained = true;

        return new ValidationResult(
            "PCIeLinkValidation",
            isLinkTrained,
            isLinkTrained ? "Link OK" : "Link training failed"
        );
    }
}
