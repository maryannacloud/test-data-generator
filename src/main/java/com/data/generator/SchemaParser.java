package com.data.generator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Purpose of the SchemaParser Class is to handle all operations related to loading and parsing the schema file.
 * It ensures reusability by allowing other components (in our case - TestDataGenerator) to use a clean and well-validated schema object.
 * We also want to make sure that we stick to a 'separation of concerns' principle.
 * SchemaParser Class ensures it by keeping the schema parsing logic separate from data generation and output handling.
 */

public class SchemaParser {

    private final ObjectMapper objectMapper;

    public SchemaParser() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Parses the schema JSON file into a Map structure.
     *
     * @param schemaPath Path to the schema JSON file.
     * @return A Map representing the schema.
     * @throws IOException If the schema file cannot be read or parsed.
     */
    public Map<String, Object> parseSchema(String schemaPath) throws IOException {

        Map<String, Object> schema = objectMapper.readValue(
            getClass().getResourceAsStream(schemaPath),
            new TypeReference<Map<String, Object>>() {}
        );

        validateSchema(schema);

        return schema;
    }

    /**
     * Validates the parsed schema for required keys and structure.
     *
     * @param schema The parsed schema Map.
     * @throws IllegalArgumentException If the schema is invalid.
     */
    private void validateSchema(Map<String, Object> schema) {
        if (!schema.containsKey("fields") || !(schema.get("fields") instanceof List)) {
            throw new IllegalArgumentException("Invalid schema: 'fields' key is missing or not a List.");
        }

        if (!schema.containsKey("rows") || !(schema.get("rows") instanceof Integer)) {
            throw new IllegalArgumentException("Invalid schema: 'rows' key is missing or not an Integer.");
        }
    }
}
