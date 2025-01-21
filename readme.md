Test Data Generator Library

The Test Data Generator library is a lightweight, configurable tool for generating realistic test data in JSON and CSV formats. This library is ideal for use in software testing, QA automation, and data-driven testing scenarios.

Features

Schema-Based Configuration: Define fields, data types, and constraints via simple configuration files (JSON).

Multiple Output Formats: Generate data in JSON and CSV formats.

Randomized Data Generation: Uses Java Faker to generate realistic random data.

Flexible Row Counts: Specify the number of rows to generate.

Extensible Design: Easily add support for more formats or custom field generators.

Installation

Prerequisites

Java 11 or higher

Maven installed on your system

Add to Your Maven Project

Include the following dependency in your pom.xml file:

<dependency>
    <groupId>com.example</groupId>
    <artifactId>test-data-generator</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

Usage

1. Define a Schema

Create a schema file (e.g., schema.json) to specify the structure of your data. For example:

{
"fields": [
{ "name": "firstName", "type": "string", "faker": "name.firstName" },
{ "name": "lastName", "type": "string", "faker": "name.lastName" },
{ "name": "age", "type": "integer", "min": 18, "max": 65 },
{ "name": "email", "type": "string", "faker": "internet.email" }
],
"rows": 100
}

2. Generate Data

Use the library to generate data based on your schema. Here’s an example:

import com.example.generator.TestDataGenerator;

public class Main {
public static void main(String[] args) throws Exception {
String schemaPath = "path/to/schema.json";
String outputPath = "output.json";

        // Generate JSON Data
        TestDataGenerator generator = new TestDataGenerator();
        generator.generate(schemaPath, outputPath, "json");

        // Generate CSV Data
        String csvOutputPath = "output.csv";
        generator.generate(schemaPath, csvOutputPath, "csv");

        System.out.println("Data generation complete!");
    }
}

3. Output Files

JSON: A file like output.json will contain your generated data in JSON format.

CSV: A file like output.csv will contain your generated data in CSV format.

Configuration Options

Supported Field Types

Type

Description

Additional Config Options

string

Generates a random string using Faker.

faker (e.g., name.firstName)

integer

Generates a random integer.

min, max

boolean

Generates a random boolean value.

None

date

Generates a random date.

format (e.g., yyyy-MM-dd)

Example Advanced Schema

{
"fields": [
{ "name": "id", "type": "integer", "min": 1, "max": 1000 },
{ "name": "name", "type": "string", "faker": "name.fullName" },
{ "name": "isActive", "type": "boolean" },
{ "name": "createdDate", "type": "date", "format": "yyyy-MM-dd" }
],
"rows": 50
}

Building and Running

Build the Project

Clone the repository and run the following Maven command:

mvn clean install

Run the Library

Compile and run your main class that uses the library:

java -cp target/test-data-generator-1.0-SNAPSHOT.jar com.example.Main

Development

Adding New Features

New Data Types: Add support for more data types by extending the FieldGenerator interface.

New Formats: Add more output formats (e.g., XML) by creating new implementations of the DataWriter interface.

Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss your ideas.

License

This project is licensed under the MIT License - see the LICENSE file for details.

Acknowledgments

Java Faker

Apache Commons CSV

Jackson