package com.github.thomasdarimont.training.api;

import com.github.thomasdarimont.training.model.TodoUpdate;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TodoResourceTest {

    @Test
    public void testFindById() {
        given().when()
                .headers(CONTENT_TYPE, APPLICATION_JSON, ACCEPT, APPLICATION_JSON)
                .get(TodoResource.ENDPOINT_ID, 1)
                .then()
                .statusCode(200)
                .body("text", is("Learn Quarkus"))
                .body("completed", is(false));
    }

    @Test
    public void updateCompletion() {
        given().when()
                .headers(CONTENT_TYPE, APPLICATION_JSON, ACCEPT, APPLICATION_JSON)
                .body(new TodoUpdate("Todo 2", true))
                .put(TodoResource.ENDPOINT_ID, 2)
                .then()
                .statusCode(202)
                .body("text", is("Todo 2"))
                .body("completed", is(true));
    }

    @Test
    public void updateTodoText() {
        given().when()
                .headers(CONTENT_TYPE, APPLICATION_JSON, ACCEPT, APPLICATION_JSON)
                .body(new TodoUpdate("Todo 3 changed", null))
                .put(TodoResource.ENDPOINT_ID, 3)
                .then()
                .statusCode(202)
                .body("text", is("Todo 3 changed"))
                .body("completed", is(false));
    }

}