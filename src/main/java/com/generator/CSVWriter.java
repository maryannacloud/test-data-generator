package com.generator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * CSVWriter class is responsible for handling the creation and writing of data to a CSV file.
 * By encapsulating this logic, we achieve modularity, reusability and cleaner code.
 * This also ensures adherence to the 'separation of concerns' principle.
 *
 * Other functions include validation of input data and headers, and exception handling.
 */

public class CSVWriter {

    /**
     * Writes the given data to a CSV file.
     *
     * @param data       The list of data to write to the file.
     * @param outputPath The path to the output CSV file.
     * @param headers    The headers for the CSV file.
     * @throws IOException If writing to the file fails.
     */
    public void writeToFile(List<Map<String, Object>> data, String outputPath, String[] headers) throws IOException {

        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data to write cannot be null or empty.");
        }

        if (outputPath == null || outputPath.isBlank()) {
            throw new IllegalArgumentException("Output path cannot be null or empty.");
        }

        if (headers == null || headers.length == 0) {
            throw new IllegalArgumentException("Headers cannot be null or empty.");
        }

        try (FileWriter writer = new FileWriter(outputPath)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder().setHeader(headers).build());

            for (Map<String, Object> row : data) {
                csvPrinter.printRecord(row.values());
            }

            System.out.printf("CSV data written to: %s%n", outputPath);
        }
    }

}
