package com.amd.validation;

/**
 * SystemInfoFetcher populates a {@link TestContext} with mock GPU metadata and telemetry.
 * <p>
 * In a real implementation, this class should gather system data from actual hardware
 * interfaces or tools such as:
 * - `rocm-smi` for AMD GPUs
 * - `nvidia-smi` for NVIDIA GPUs
 * - `dxdiag` or `WMI` for Windows
 * </p>
 */
public final class SystemInfoFetcher {

    // Private constructor to prevent instantiation of utility class
    private SystemInfoFetcher() {}

    /**
     * Fills the provided {@link TestContext} with sample GPU data for testing.
     * <p>
     * Replace this mock data with real telemetry in production environments.
     *
     * @param ctx the {@link TestContext} instance to populate
     */
    public static void populate(TestContext ctx) {
        // Static device properties
        ctx.setDeviceName("AMD Radeon (TM) Graphics");
        ctx.setPciLocation("PCI bus 4, device 0, function 0");
        ctx.setDriverVersion("31.0.21914.6001");

        // Memory telemetry (in GB)
        ctx.setTotalMemoryGB(8.0);
        ctx.setMemoryUsedGB(1.5);
        ctx.setDedicatedMemoryGB(2.0);
        ctx.setSharedMemoryGB(6.0);

        // Real-time sensor values
        ctx.setGpuTemperature(54.0);    // Temperature in Celsius
        ctx.setFanSpeedRPM(1200);       // Fan speed in RPM
        ctx.setPowerWatts(110);         // Power consumption in Watts
    }
}
