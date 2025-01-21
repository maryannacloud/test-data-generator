package com.data.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataGenerator {

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

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> schema = objectMapper.readValue(getClass().getResourceAsStream(schemaPath), Map.class);


        List<Map<String, Object>> fields = (List<Map<String, Object>>) schema.get("fields");
        int rows = (int) schema.get("rows");

        List<Map<String, Object>> data = generateData(fields, rows);

        if ("json".equalsIgnoreCase(format)) {
            writeJson(data, outputPath);
        } else if ("scv".equalsIgnoreCase(format)) {
            writeCsv(data, outputPath, fields);
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
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
                    case "string" -> record.put(name, generateString(field));
                    case "integer" -> record.put(name, generateInteger(field));
                    case "boolean" -> record.put(name, faker.bool().bool());
                    default -> throw new IllegalArgumentException("Unsupported field type: " + type);
                }
            }
            data.add(record);
        }
        return data;
    }
}
