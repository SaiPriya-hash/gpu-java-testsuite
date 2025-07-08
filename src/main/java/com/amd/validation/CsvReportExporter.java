package com.amd.validation;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Writes a TestReport to disk as a CSV file.
 */
public final class CsvReportExporter {

    private CsvReportExporter() { /* utility class */ }

    public static void export(TestReport report, Path out) throws IOException {
        try (CSVWriter w = new CSVWriter(new FileWriter(out.toFile()))) {
            // header
            w.writeNext(new String[]{
                "Task", "Status", "Message",
                "Device ID", "Device Name", "Driver", "PCIe Location"
            });
            for (ValidationResult r : report.getResults()) {
                TestContext c = report.getContext();
                w.writeNext(new String[]{
                    r.getTaskName(),
                    r.isPassed() ? "PASS" : "FAIL",
                    r.getMessage(),
                    c.getDeviceId(),
                    c.getDeviceName(),
                    c.getDriverVersion(),
                    c.getPciLocation()
                });
            }
        }
    }
}
