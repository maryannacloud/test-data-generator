package com.generator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The JSONWriter class is responsible for writing the generated data to a JSON file.
 * JSONWriter ensures reusability by allowing other components (like TestDataGenerator) to write JSON files without duplicating the logic.
 * JSONWriter class also has validation logic, which provides a meaningful error message if writing fails
 *
 */

public class JSONWriter {

    private final ObjectMapper objectMapper;

    public JSONWriter() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Writes the given data to a JSON file.
     *
     * @param data The list of data to write to the file.
     * @param outputPath The path to the output JSON file.
     * @throws IOException If writing to the file fails.
     */
    public void writeToFile(List<Map<String, Object>> data, String outputPath) throws IOException {

        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data is null or empty.");
        }

        if(outputPath == null || outputPath.isBlank()) {
            throw new IllegalArgumentException("Output path is null or empty.");
        }

        File outputFile = new File(outputPath);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, data);

        System.out.printf("JSON data written to: %s%n", outputPath);

    }
}
