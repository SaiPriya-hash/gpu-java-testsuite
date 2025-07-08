package com.amd.validation;

/**
 * PCIeLocationValidation checks whether the GPU's actual PCIe bus/device/function
 * location matches the expected value.
 *
 * Useful for factory setups where GPU placement is deterministic and any deviation
 * may indicate incorrect assembly or board enumeration.
 */
public class PCIeLocationValidation implements ValidationTask {

    // SLF4J logger for structured logging
    private static final org.slf4j.Logger LOG =
            org.slf4j.LoggerFactory.getLogger(PCIeLocationValidation.class);

    // Expected PCIe location string, e.g., "PCI bus 4, device 0, function 0"
    private final String expectedLocation;

    /**
     * Constructs the validator with the expected PCIe location string.
     *
     * @param expectedLocation The expected PCIe BDF string
     */
    public PCIeLocationValidation(String expectedLocation) {
        this.expectedLocation = expectedLocation;
    }

    /**
     * Executes the PCIe location validation logic.
     *
     * @param context The test context containing the actual PCIe location
     * @return ValidationResult containing the match status and message
     */
    @Override
    public ValidationResult execute(TestContext context) {
        String actual = context.getPciLocation();
        boolean match = expectedLocation.equalsIgnoreCase(actual);

        LOG.info("Validating PCIe location: Expected = {}, Actual = {}", expectedLocation, actual);

        return new ValidationResult(
            "PCIeLocationValidation",
            match,
            match ? "PCIe location matched" : "PCIe location mismatch"
        );
    }
}
