import com.generator.JSONWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

private final String outputPath = "test-output.json";
private JSONWriter jsonWriter;

@BeforeEach
void setUp() {
    jsonWriter = new JSONWriter();
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

    // Act
    jsonWriter.writeToFile(data, outputPath);

    // Assert
    File file = new File(outputPath);
    assertTrue(file.exists(), "Output file should exist");
    String fileContent = Files.readString(file.toPath());
    assertTrue(fileContent.contains("John Doe"), "File should contain 'John Doe'");
    assertTrue(fileContent.contains("Jane Smith"), "File should contain 'Jane Smith'");
}

@Test
void testWriteToFile_NullData_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> jsonWriter.writeToFile(null, outputPath));
}

@Test
void testWriteToFile_EmptyData_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> jsonWriter.writeToFile(List.of(), outputPath));
}

@Test
void testWriteToFile_InvalidOutputPath_ThrowsException() {

    List<Map<String, Object>> data = List.of(Map.of("key", "value"));
    assertThrows(IllegalArgumentException.class, () -> jsonWriter.writeToFile(data, ""));
}

public void main() {
}