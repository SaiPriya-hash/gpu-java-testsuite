package com.amd.validation;

/**
 * TestContext encapsulates the full runtime context of a GPU device during validation.
 * <p>
 * This includes both static metadata (e.g., device ID, PCIe location) and dynamic telemetry
 * (e.g., memory usage, temperature, fan speed). It serves as the input/output hub across
 * all {@link ValidationTask} implementations and is optionally used in export/report tools.
 * </p>
 */
public class TestContext {

    /** Unique identifier for the GPU under validation (e.g., GPU-001, GPU-A1) */
    private final String deviceId;

    /** Human-readable name of the GPU (e.g., "AMD Radeon RX 7900 XT") */
    private String deviceName;

    /** PCIe location of the GPU (e.g., "PCI bus 1, device 0, function 0") */
    private String pciLocation;

    /** Installed driver version managing this GPU (e.g., "31.0.21914.6001") */
    private String driverVersion;

    /** Total available memory for the GPU (in gigabytes) */
    private double totalMemoryGB;

    /** Currently used memory on the GPU (in gigabytes) */
    private double memoryUsedGB;

    /** Dedicated VRAM allocated exclusively to this GPU (in gigabytes) */
    private double dedicatedMemoryGB;

    /** System memory shared with the GPU, if applicable (in gigabytes) */
    private double sharedMemoryGB;

    /** Real-time temperature reading of the GPU (in degrees Celsius) */
    private double gpuTemperature;

    /** Fan speed of the GPU cooling unit (in RPM) */
    private int fanSpeedRPM;

    /** Current power draw of the GPU (in watts) */
    private int powerWatts;

    /**
     * Constructs a new {@code TestContext} for a given device ID.
     *
     * @param deviceId the unique identifier to assign (e.g., "GPU-001")
     */
    public TestContext(String deviceId) {
        this.deviceId = deviceId;
    }

    /** @return the unique device ID */
    public String getDeviceId() {
        return deviceId;
    }

    /** @return the human-readable device name */
    public String getDeviceName() {
        return deviceName;
    }

    /** @param deviceName sets the human-readable device name */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /** @return the PCIe location string */
    public String getPciLocation() {
        return pciLocation;
    }

    /** @param pciLocation sets the PCIe location */
    public void setPciLocation(String pciLocation) {
        this.pciLocation = pciLocation;
    }

    /** @return the installed driver version */
    public String getDriverVersion() {
        return driverVersion;
    }

    /** @param driverVersion sets the driver version */
    public void setDriverVersion(String driverVersion) {
        this.driverVersion = driverVersion;
    }

    /** @return total available memory in GB */
    public double getTotalMemoryGB() {
        return totalMemoryGB;
    }

    /** @param totalMemoryGB sets the total GPU memory in GB */
    public void setTotalMemoryGB(double totalMemoryGB) {
        this.totalMemoryGB = totalMemoryGB;
    }

    /** @return currently used memory in GB */
    public double getMemoryUsedGB() {
        return memoryUsedGB;
    }

    /** @param memoryUsedGB sets the current memory usage in GB */
    public void setMemoryUsedGB(double memoryUsedGB) {
        this.memoryUsedGB = memoryUsedGB;
    }

    /** @return dedicated memory allocated to the GPU in GB */
    public double getDedicatedMemoryGB() {
        return dedicatedMemoryGB;
    }

    /** @param dedicatedMemoryGB sets the GPU's dedicated memory in GB */
    public void setDedicatedMemoryGB(double dedicatedMemoryGB) {
        this.dedicatedMemoryGB = dedicatedMemoryGB;
    }

    /** @return shared memory accessible by the GPU in GB */
    public double getSharedMemoryGB() {
        return sharedMemoryGB;
    }

    /** @param sharedMemoryGB sets the GPU's shared memory in GB */
    public void setSharedMemoryGB(double sharedMemoryGB) {
        this.sharedMemoryGB = sharedMemoryGB;
    }

    /** @return current GPU temperature in Celsius */
    public double getGpuTemperature() {
        return gpuTemperature;
    }

    /** @param gpuTemperature sets the GPU temperature in Celsius */
    public void setGpuTemperature(double gpuTemperature) {
        this.gpuTemperature = gpuTemperature;
    }

    /** @return fan speed in RPM */
    public int getFanSpeedRPM() {
        return fanSpeedRPM;
    }

    /** @param fanSpeedRPM sets the GPU fan speed in RPM */
    public void setFanSpeedRPM(int fanSpeedRPM) {
        this.fanSpeedRPM = fanSpeedRPM;
    }

    /** @return current GPU power consumption in watts */
    public int getPowerWatts() {
        return powerWatts;
    }

    /** @param powerWatts sets the GPU power draw in watts */
    public void setPowerWatts(int powerWatts) {
        this.powerWatts = powerWatts;
    }
}