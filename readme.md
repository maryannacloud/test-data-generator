Test Data Generator Library

The Test Data Generator library is a lightweight, configurable tool for generating realistic test data in JSON and CSV formats. This library is ideal for use in software testing, QA automation, and data-driven testing scenarios.

Features

1. Schema-Based Configuration (define fields, data types, and constraints via simple configuration files (JSON)).

2. Multiple Output Formats (generate data in JSON and CSV formats).

3. Randomized Data Generation (uses Java Faker to generate realistic random data).

4. Flexible Row Counts (specify the number of rows to generate).

5. Extensible Design (easily add support for more formats or custom field generators).

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
