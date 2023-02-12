package com.emurugova;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void getSingleUser () {
    Integer user = 2;
    given()
                .contentType(JSON)
                .when()
                .get("/api/users/"+user)
                .then()
                .statusCode(200)
                .body("data.id", is(user));
}

    @Test
    void getSingleUserWithAssertInteger () {
        Integer user = 2;
        Integer response = get ("/api/users/"+user)
                          .then()
                          .extract()
                          .path("data.id");
        assertEquals(2,response);
    }

    @Test
    void getSingleUserWithAssertString () {
        Integer user = 2;
        String response = get ("/api/users/"+user)
                .then()
                .extract()
                .response()
                .asString();
        assertThat(response).contains("janet.weaver@reqres.in");
    }

    @Test
    void getSingleUserNotFound () {
        Integer user = 23;
        given()
                .contentType(JSON)
                .when()
                .get("/api/users/"+user)
                .then()
                .statusCode(404);
    }

    @Test
    void createNewUser () {
        String data = "{\"name\": \"morpheus\", \"job\": \"leader\"}";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/users/")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void updateUser () {
        Integer user = 2;
        String data = "{ \"name\": \"superman\", \"job\": \"zion resident\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .put("/api/users/"+user)
                .then()
                .statusCode(200)
                .body("name", is("superman"))
                .body("job", is("zion resident"));
    }

    @Test
    void deleteUser () {
        Integer user = 2;
        given()
                .contentType(JSON)
                .when()
                .delete("/api/users/"+user)
                .then()
                .statusCode(204);
    }

}


