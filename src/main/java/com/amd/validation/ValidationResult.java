package com.amd.validation;

public class ValidationResult {
    private final String taskName;
    private final boolean passed;
    private final String message;

    public ValidationResult(String taskName, boolean passed, String message) {
        this.taskName = taskName;
        this.passed = passed;
        this.message = message;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isPassed() {
        return passed;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "taskName='" + taskName + '\'' +
                ", passed=" + passed +
                ", message='" + message + '\'' +
                '}';
    }
}
