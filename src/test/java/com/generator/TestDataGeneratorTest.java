package com.generator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataGeneratorTest {

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
        List<Map<String, Object>> fields = schemaParser.parseSchema("src/main/resources/schema.json");

        List<Map<String, Object>> data = testDataGenerator.generateData(fields, 5);

        // Validate the schema
        assertNotNull(data, "Generated data should not be null.");
        assertFalse(data.isEmpty(), "Generated data should not be empty.");

        // Validate each row matches schema columns (e.g., 4 columns in schema.json)
        int expectedColumnCount = 4; // Replace with the actual column count from your schema
        for (Map<String, Object> row : data) {
            assertEquals(expectedColumnCount, row.size(), "Row does not match schema column count.");
        }

    }

    @Test
    public void testGeneratedDataTypes() {
        // Generate test data
        List<String[]> data = testDataGenerator.generateData("schema.json", 5);

        // Validate data types (assuming schema.json specifies types for each column)
        for (String[] row : data) {
            // Replace column indexes with actual schema-defined column indexes
            assertDoesNotThrow(() -> Integer.parseInt(row[0]), "ID column should be an integer.");
            assertNotNull(row[1], "Name column should not be null."); // Assuming name is a string
            assertDoesNotThrow(() -> Boolean.parseBoolean(row[2]), "Active column should be a boolean.");
        }
    }

    @Test
    public void testGenerateDataFile() {
        // Generate data and save to file
        String filePath = "test_data.csv";
        testDataGenerator.generateDataFile("schema.json", 10, filePath);

        // Verify the file exists
        File file = new File(filePath);
        assertTrue(file.exists(), "Generated data file should exist.");

        // Verify file is not empty
        assertTrue(file.length() > 0, "Generated data file should not be empty.");

        // Clean up
        file.delete();
    }
}
