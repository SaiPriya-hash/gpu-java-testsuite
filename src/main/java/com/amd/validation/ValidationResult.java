package com.amd.validation;

/**
 * Represents the outcome of a single validation task.
 *
 * <p>This class is used to encapsulate the result of executing a {@link ValidationTask},
 * including whether the task passed, a descriptive message, and the task name.</p>
 */
public class ValidationResult {

    /** Name of the task that produced this result (e.g., "PCIeLinkValidation") */
    private final String taskName;

    /** Indicates whether the task passed (true) or failed (false) */
    private final boolean passed;

    /** Additional message describing the result, useful for logs or reports */
    private final String message;

    /**
     * Constructs a new ValidationResult.
     *
     * @param taskName the name of the validation task
     * @param passed   true if the task passed; false if it failed
     * @param message  a descriptive message (e.g., error reason or success confirmation)
     */
    public ValidationResult(String taskName, boolean passed, String message) {
        this.taskName = taskName;
        this.passed = passed;
        this.message = message;
    }

    /**
     * Gets the name of the validation task that produced this result.
     *
     * @return the task name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Indicates whether the task passed.
     *
     * @return true if validation passed; false otherwise
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * Gets the descriptive message associated with the result.
     *
     * @return result message (e.g., "OK", "Link Down", "Temp exceeded threshold")
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns a formatted string describing the result.
     *
     * @return a readable string summary of the validation result
     */
    @Override
    public String toString() {
        return "ValidationResult{" +
               "taskName='" + taskName + '\'' +
               ", passed=" + passed +
               ", message='" + message + '\'' +
               '}';
    }
}
