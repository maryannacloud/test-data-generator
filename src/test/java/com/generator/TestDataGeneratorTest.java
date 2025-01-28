package com.generator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataGeneratorTest {

    private static final Logger logger = LoggerFactory.getLogger(TestDataGeneratorTest.class);

    private TestDataGenerator testDataGenerator;

    @BeforeEach
    public void setUp() {
        testDataGenerator = new TestDataGenerator();
    }

    @AfterEach
    public void tearDown() {
        testDataGenerator = null;
    }

    @Test
    public void testGeneratedDataMatchesSchema() throws IOException {

        SchemaParser schemaParser = new SchemaParser();
        List<Map<String, Object>> fields = schemaParser.getFields("src/main/resources/schema.json");

        Map<String, Object> schema = schemaParser.parseSchema("src/main/resources/schema.json");
        int rows = (int) schema.get("rows");

        List<Map<String, Object>> data = testDataGenerator.generateData(fields, rows);

        assertNotNull(data, "Generated data should not be null.");
        assertFalse(data.isEmpty(), "Generated data should not be empty.");

        int expectedColumnCount = fields.size();
        for (Map<String, Object> row : data) {
            assertEquals(expectedColumnCount, row.size(), "Row does not match schema column count.");
        }
    }

    @Test
    public void testGeneratedDataTypes() throws IOException {
        SchemaParser schemaParser = new SchemaParser();
        Map<String, Object> schema = schemaParser.parseSchema("src/main/resources/schema.json");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> fields = (List<Map<String, Object>>) schema.get("fields");
        int rows = (int) schema.get("rows");

        List<Map<String, Object>> data = testDataGenerator.generateData(fields, rows);

        for (Map<String, Object> row : data) {
            for (Map<String, Object> field : fields) {
                String columnName = (String) field.get("name");
                String columnType = (String) field.get("type");

                Object value = row.get(columnName);

                switch (columnType) {
                    case "string" -> assertInstanceOf(String.class, value,
                            String.format("%s should be a string.", columnName));
                    case "integer" -> assertInstanceOf(Integer.class, value,
                            String.format("%s should be an integer.", columnName));
                    case "boolean" -> assertInstanceOf(Boolean.class, value,
                            String.format("%s should be a boolean.", columnName));
                    default -> fail(String.format("Unsupported type: %s.", columnType));
                }
            }
        }
    }

    @Test
    public void testGeneratedDataFile() throws IOException {
        String filePath = "test_data.csv";
        testDataGenerator.generateDataFile("src/main/resources/schema.json", 10, filePath);

        File file = new File(filePath);
        assertTrue(file.exists(), "Generated data file should exist.");
        assertTrue(file.length() > 0, "Generated data file should not be empty.");

        if (!file.delete()) {
            logger.warn("Failed to delete file: {}", file.getAbsolutePath());
        }
    }
}
