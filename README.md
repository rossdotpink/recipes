# tw.ross.recipes

## What this code does

This project provides a RESTful web service for managing recipies.

With it, you can:
- create, read, update and delete recipes
- search for recipes

## Prerequisites

- Java 19
- Maven

## Setup

1. ensure your machine meets the prerequisites
2. checkout the code
3. run `mvn package` to build a bootable `.jar` inside `target/`
4. run `mvn wildfly-jar:run` to run it