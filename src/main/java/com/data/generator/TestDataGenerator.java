package com.data.generator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataGenerator {

    private static final Logger logger = LoggerFactory.getLogger(TestDataGenerator.class);

    private final Faker faker = new Faker();

    /**
     * Generates test data based on the given schema and writes it to the specified output file.
     *
     * @param schemaPath Path to the schema file (JSON).
     * @param outputPath Path to the output file.
     * @param format     Output format (\"json\" or \"csv\").
     * @throws IOException If file operations fail.
     */
    public void generate(String schemaPath, String outputPath, String format) throws IOException {

        SchemaParser schemaParser = new SchemaParser();
        Map<String, Object> schema = schemaParser.parseSchema(schemaPath);

        List<Map<String, Object>> fields = new ObjectMapper()
                .convertValue(schema.get("fields"),
                new TypeReference<List<Map<String, Object>>>(){});

        int rows = (int) schema.get("rows");

        List<Map<String, Object>> data = generateData(fields, rows);

        if ("json".equalsIgnoreCase(format)) {
            writeJson(data, outputPath);
        } else if ("csv".equalsIgnoreCase(format)) {
            writeCsv(data, outputPath, fields);
        } else {
            throw new IllegalArgumentException(String.format("Unsupported format: %s", format));
        }
    }

    private List<Map<String, Object>> generateData(List<Map<String, Object>> fields, int rows) {

        List<Map<String, Object>> data = new ArrayList<>();

        for (int i = 0; i < rows; i++) {

            Map<String, Object> row = new HashMap<>();

            for (Map<String, Object> field : fields) {
                String name = (String) field.get("name");
                String type = (String) field.get("type");

                switch (type) {
                    case "string" -> row.put(name, generateString(field));
                    case "integer" -> row.put(name, generateInteger(field));
                    case "boolean" -> row.put(name, faker.bool().bool());
                    default -> throw new IllegalArgumentException(String.format("Unsupported field type: %s", type));
                }
            }
            data.add(row);
        }
        return data;
    }

    private String generateString(Map<String, Object> field) {
        String fakerKey = (String) field.get("faker");
        if (fakerKey != null) {
            // Use Faker to generate a string
            String[] parts = fakerKey.split("\\.");
            try {
                return (String) faker.getClass()
                        .getMethod(parts[0])
                        .invoke(faker)
                        .getClass()
                        .getMethod(parts[1])
                        .invoke(faker.getClass().getMethod(parts[0]).invoke(faker));
            } catch (Exception e) {
                throw new RuntimeException(String.format("Invalid Faker key: %s", fakerKey), e);
            }
        }
        return faker.lorem().word();
    }

    private Integer generateInteger(Map<String, Object> field) {
        int min = (int) field.getOrDefault("min", 0);
        int max = (int) field.getOrDefault("max", 100);
        return faker.number().numberBetween(min, max);
    }

    private String generateDate(Map<String, Object> field) {
        String format = (String) field.getOrDefault("format", "yyyy-MM-dd");
        return faker.date().birthday().toInstant().toString().split("T")[0];
    }

    private void writeJson(List<Map<String, Object>> data, String outputPath) throws IOException {
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeToFile(data, outputPath);
    }

    private void writeCsv(List<Map<String, Object>> data, String outputPath, List<Map<String, Object>> fields) throws IOException {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToFile(data, outputPath, getHeaders(fields));
    }

    private String[] getHeaders(List<Map<String, Object>> fields) {
        return fields.stream().map(field -> (String) field.get("name")).toArray(String[]::new);
    }

    public static void main(String[] args) {
        try {
            String schemaPath = "src/main/resources/schema.json";
            String jsonOutputPath = "output.json";
            String csvOutputPath = "output.csv";

            TestDataGenerator generator = new TestDataGenerator();
            generator.generate(schemaPath, jsonOutputPath, "json");
            generator.generate(schemaPath, csvOutputPath, "csv");

        } catch (Exception e) {
            logger.error("An error occurred while generating test data", e);
        }
    }
}
