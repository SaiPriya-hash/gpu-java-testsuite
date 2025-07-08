package com.amd.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Serialises a TestReport to formatted JSON.
 */
public final class JsonReportExporter {

    private static final ObjectMapper MAPPER =
            new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private JsonReportExporter() { /* utility class */ }

    public static void export(TestReport report, Path out) throws IOException {
        MAPPER.writeValue(out.toFile(), report);
    }
}
