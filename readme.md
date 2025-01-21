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
