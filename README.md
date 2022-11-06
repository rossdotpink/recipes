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

By default, it will run on http://127.0.0.1:8080/recipes

## Documentation

The code itself contains some JavaDoc comments. There is also a Postman collection for testing and an OpenAPI spec which can be used with e.g. Swagger UI or the online Swagger Editor.

# Structure 
## Package structure

### `tw.ross.recipes`
This is the top level package, containing the JAX-RS / Jakarta EE REST API.

In here is only the `RecipesApplication` which provides the entrypoint to the Jakarta EE app; there is no `main` function.

Packages beneath this one are based on common purpose, rather than common type i.e. all the `/recipe` code is in a distinct package etc., rather than just all the `model` code in a package and the `api` code in a package etc.

#### `tw.ross.recipes.recipe`
This package contains everything to provide the functionality at `/recipes`.

It includes the model i.e. the Recipe class, a 'resource' layer i.e the HTTP endpoints, and a 'service' layer i.e. the persistence layer.

#### `tw.ross.recipes.search`
This package contains everything to provide the functionality at `/search`.

It includes the model i.e. the RecipeSearch class, and a 'resource' layer i.e the HTTP endpoints. Since searches are not persisted, there is no 'service' layer.

## Design decisions
As much as possible, the document [RESTful web API design](https://learn.microsoft.com/en-us/azure/architecture/best-practices/api-design) was used to guide the API design.

The **API uses JSON** exclusively to send and receive payloads. JAX-RS / Jakarta EE provide mechanisms for automatically marshalling JSON to and from Java objects, which is used extensively. JSON is also easier to work with compared to XML.

A **two-tier approach** was used, such that for each endpoint (e.g. `/recipe`) there is a frontend 'resource' layer and a backend 'service' layer for persistence where appropriate.

**Searches are treated as a resource** with their own endpoint (e.g. `/search/recipe`) underneath the main path (`/search`). This would allow for later inclusion of other searchable resources e.g. recipe lists or dietary preferences, at their own path.

## Outstanding items

I have not implemented any **integration tests**. To do this, I would look to write more JUnit tests, but rather than mocking some components (as with the unit tests), I would simply incorporate multiple components to test that they _integrate_ properly.

I have not written as many **unit tests** as I would like. I have written quite a few though, which should give an idea of my thoughts on how to perform testing.

I have not documented the API or the code as much as I would have liked. I would like at least to write more JavaDocs-style comments for the main functions.

I'm not sure I would say the code is exactly 'production ready' (though I must say, that's a very cheeky requirement considering I'm not being paid for this 😉 ). Anyway, to help make this more production ready, I would especially like to:
- implement instrumentation and logging e.g. Azure Application Insights, OpenTelemetry, etc.
- make exception handling more robust (I have not yet figured out how to handle exceptions globally e.g. JSON marshalling exceptions, before my GET/POST/etc. endpoints are called.)

## Criticisms

Whilst the `recipe` and `search` packages are separate, they are rather **tightly coupled**. This is because the ability to search recipes is **highly dependent on the recipe model**. I am sure there would be a way to use reflection and/or generics, to make the search functionality more modular; packages with a model they wish to make modular would then be able to simply register themselves with the `search` resource.

There are only classes and **no interfaces** used. This is not so bad _right now_ since the project is small, but is obviously not scalable. I think as soon as another resource was added besides Recipe, it would become clearer to me where it would make sense to draw interface boundaries.

As stated already, exception handling within some of the JAX-RS / Jakarta EE framework is not taken care of. Malformed calls will return a `500` code with a plaintext error (not even JSON). This is something I did not manage to find a good solution for yet.
