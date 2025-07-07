package com.amd.validation;

public class ThermalMonitorCheck implements ValidationTask {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PCIeLinkValidation.class);

    @Override
    public ValidationResult execute(TestContext context) {
    	LOG.info("Running Thermal Monitor Check on Device: " + context.getDeviceId());
    	/* TODO real logic */
        return new ValidationResult("ThermalMonitorCheck", true, "Link OK");
        
    }
}
