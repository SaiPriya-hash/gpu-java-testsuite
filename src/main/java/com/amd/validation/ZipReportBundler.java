package com.amd.validation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Bundles all files in a directory into a single ZIP archive.
 */
public final class ZipReportBundler {

    private ZipReportBundler() { /* utility class */ }

    public static void bundleReports(Path dir, Path zipPath) throws IOException {
        try (OutputStream fos = Files.newOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            Files.walk(dir)
                 .filter(Files::isRegularFile)
                 .forEach(file -> addToZip(dir, file, zos));
        }
    }

    private static void addToZip(Path baseDir, Path file, ZipOutputStream zos) {
        ZipEntry entry = new ZipEntry(baseDir.relativize(file).toString());
        try (InputStream in = Files.newInputStream(file)) {
            zos.putNextEntry(entry);
            in.transferTo(zos);
            zos.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException("Failed to zip " + file, e);
        }
    }
}
