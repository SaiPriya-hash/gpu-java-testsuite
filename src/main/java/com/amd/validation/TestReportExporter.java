package com.amd.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * TestReportExporter handles exporting a {@link TestReport} to multiple formats:
 * - CSV for tabular data
 * - JSON for structured, machine-readable format
 * - ZIP archive bundling all report files
 */
public class TestReportExporter {

    /**
     * Exports the given report to CSV and JSON formats, then bundles them into a ZIP file.
     *
     * @param report the validation test report to export
     * @return path to the generated ZIP file
     * @throws IOException if any file operation fails
     */
    public Path export(TestReport report) throws IOException {
        // Format the output folder name using a timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        Path reportDir = Paths.get("target", "reports", "run-" + timestamp);
        Files.createDirectories(reportDir);

        // Generate CSV and JSON files inside the report directory
        Path csvPath = reportDir.resolve("report.csv");
        writeCsv(report, csvPath);

        Path jsonPath = reportDir.resolve("report.json");
        writeJson(report, jsonPath);

        // Bundle the entire report directory into a zip file
        Path zipPath = Paths.get("target", "reports", "run-" + timestamp + ".zip");
        zipDirectory(reportDir, zipPath);

        return zipPath;
    }

    /**
     * Writes the test report results into a CSV file with basic device metadata.
     */
    private void writeCsv(TestReport report, Path csvPath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvPath.toFile()))) {
            // CSV header
            writer.writeNext(new String[] {
                "Task Name", "Passed", "Message",
                "Device ID", "Device Name", "PCI Location",
                "Driver Version", "Total Mem (GB)"
            });

            // Each row represents a validation result
            for (ValidationResult r : report.getResults()) {
                TestContext ctx = report.getContext();
                writer.writeNext(new String[] {
                    r.getTaskName(),
                    r.isPassed() ? "PASS" : "FAIL",
                    r.getMessage(),
                    ctx.getDeviceId(),
                    ctx.getDeviceName(),
                    ctx.getPciLocation(),
                    ctx.getDriverVersion(),
                    String.valueOf(ctx.getTotalMemoryGB())
                });
            }
        }
    }

    /**
     * Serializes the entire TestReport as indented JSON using Jackson.
     */
    private void writeJson(TestReport report, Path jsonPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(jsonPath.toFile(), report);
    }

    /**
     * Creates a ZIP archive of the specified directory.
     */
    private void zipDirectory(Path sourceDir, Path zipFilePath) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath.toFile()))) {
            Files.walk(sourceDir)
                 .filter(Files::isRegularFile)
                 .forEach(path -> {
                     ZipEntry entry = new ZipEntry(sourceDir.relativize(path).toString());
                     try (InputStream in = new FileInputStream(path.toFile())) {
                         zipOut.putNextEntry(entry);
                         in.transferTo(zipOut);
                         zipOut.closeEntry();
                     } catch (IOException e) {
                         throw new RuntimeException("Failed to zip report file: " + path, e);
                     }
                 });
        }
    }
}
