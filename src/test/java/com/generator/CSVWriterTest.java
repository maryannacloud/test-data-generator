package com.generator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSVWriterTest {

    private final String outputPath = "test-output.csv";
    private CSVWriter csvWriter;

    @BeforeEach
    void setUp() {
        csvWriter = new CSVWriter();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up test file
        File file = new File(outputPath);
        if (file.exists()) {
            Files.delete(file.toPath());
        }
    }

    @Test
    void testWriteToFile_ValidData_Success() throws IOException {
        // Arrange
        List<Map<String, Object>> data = List.of(
                Map.of("name", "John Doe", "age", 30, "active", true),
                Map.of("name", "Jane Smith", "age", 25, "active", false)
        );
        String[] headers = {"name", "age", "active"};

        // Act
        csvWriter.writeToFile(data, outputPath, headers);

        // Assert
        File file = new File(outputPath);
        assertTrue(file.exists(), "Output file should exist");
        String fileContent = Files.readString(file.toPath());
        assertTrue(fileContent.contains("John Doe"), "File should contain 'John Doe'");
        assertTrue(fileContent.contains("Jane Smith"), "File should contain 'Jane Smith'");
    }

    @Test
    void testWriteToFile_NullData_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> csvWriter.writeToFile(null, outputPath, new String[]{"header"}));
    }

    @Test
    void testWriteToFile_EmptyData_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> csvWriter.writeToFile(List.of(), outputPath, new String[]{"header"}));
    }

    @Test
    void testWriteToFile_NullHeaders_ThrowsException() {
        // Arrange
        List<Map<String, Object>> data = List.of(Map.of("key", "value"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> csvWriter.writeToFile(data, outputPath, null));
    }

    @Test
    void testWriteToFile_EmptyHeaders_ThrowsException() {
        // Arrange
        List<Map<String, Object>> data = List.of(Map.of("key", "value"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> csvWriter.writeToFile(data, outputPath, new String[]{}));
    }
}
