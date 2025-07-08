package com.amd.validation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Entry point for the GPU Factory Test Framework.
 *
 * Supported CLI flags
 * -------------------
 * --device-id <id>          : GPU identifier (default: GPU-001)
 * --tasks pcie,thermal,...  : Comma‑separated list of tasks to run
 *                             (pcie, thermal, memory, fan, power)
 * --export                  : Write report.csv / report.json and ZIP bundle
 * --summary-only            : Suppress per‑task info logs
 */
public class MainApp {

    private static final org.slf4j.Logger LOG =
            org.slf4j.LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception {
        System.out.println("Starting GPU Factory Validation Framework...");

        /* -------------------------------------------------------
         * 1. Parse CLI flags
         * ----------------------------------------------------- */
        Map<String, String> flags   = parseArgs(args);
        boolean export              = flags.containsKey("--export");
        boolean summaryOnly         = flags.containsKey("--summary-only");
        String deviceId             = flags.getOrDefault("--device-id", "GPU-001");
        String taskCsv              = flags.getOrDefault("--tasks",
                                     "pcie,thermal,memory,fan,power");

        /* -------------------------------------------------------
         * 2. Build test context (metadata + telemetry)
         * ----------------------------------------------------- */
        TestContext ctx = new TestContext(deviceId);
        SystemInfoFetcher.populate(ctx);   // mock data; replace with real sensors

        /* -------------------------------------------------------
         * 3. Resolve and execute validation tasks
         * ----------------------------------------------------- */
        List<ValidationTask> tasks  = resolveTasks(taskCsv, summaryOnly);
        if (tasks.isEmpty()) {
            System.err.println("No valid tasks requested. Exiting.");
            return;
        }
        TestReport report = new TestRunner(tasks).runAll(ctx);

        /* -------------------------------------------------------
         * 4. Console summary
         * ----------------------------------------------------- */
        System.out.println(report);

        /* -------------------------------------------------------
         * 5. Optional export to CSV / JSON / ZIP
         * ----------------------------------------------------- */
        if (export) {
            Path resultDir = Path.of("target", "test-results");
            Files.createDirectories(resultDir);

            Path csvPath = resultDir.resolve("report.csv");
            Path jsonPath = resultDir.resolve("report.json");
            Path zipPath  = resultDir.resolve("report_bundle.zip");

            CsvReportExporter.export(report, csvPath);
            JsonReportExporter.export(report, jsonPath);
            ZipReportBundler.bundleReports(resultDir, zipPath);

            LOG.info("Reports written to {}", resultDir.toAbsolutePath());
        }

        System.out.println("Validation complete.");
    }

    /* ========== helper methods =========================================== */

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String flag = args[i];
            if (flag.startsWith("--")) {
                if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
                    map.put(flag, args[++i]);
                } else {
                    map.put(flag, "true");
                }
            }
        }
        return map;
    }

    private static List<ValidationTask> resolveTasks(String csv, boolean summaryOnly) {
        Set<String> want = Arrays.stream(csv.split(","))
                                 .map(String::trim)
                                 .map(String::toLowerCase)
                                 .collect(Collectors.toSet());

        List<ValidationTask> list = new ArrayList<>();
        if (want.contains("pcie"))    list.add(new PCIeLinkValidation(summaryOnly));
        if (want.contains("thermal")) list.add(new ThermalMonitorCheck(summaryOnly));
        if (want.contains("memory"))  list.add(new GpuMemoryUsageValidation());
        if (want.contains("fan"))     list.add(new FanSpeedValidation());
        if (want.contains("power"))   list.add(new PowerConsumptionValidation());
        return list;
    }
}
