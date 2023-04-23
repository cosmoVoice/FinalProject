package Blog;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;


public class TestBlog {
    private final String POSTURL = "https://test-stand.gb.ru/gateway/login";
    private final String USERNAME = "2763";
    private final String PASSWORD = "f804d21145";
    private final String URL = "https://test-stand.gb.ru/api/posts";
    private final String TOKEN = "7866f9e8b1254388256f95ed9e126552";

    RequestSpecification requestSpecification;

    @BeforeEach
    void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader("X-Auth-Token", TOKEN)
                .build();
    }

    @Test
    void getTokenTest() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", USERNAME)
                .formParam("password", PASSWORD)
                .when()
                .post(POSTURL)
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    void getTokenWithEmptyPasswordTest() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", USERNAME)
                .formParam("password","")
                .when()
                .post(POSTURL)
                .then()
                .assertThat()
                .statusCode(401);
    }
    @Test
    void getTokenWithEmptyUsernameTest() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "")
                .formParam("password", PASSWORD)
                .when()
                .post(POSTURL)
                .then()
                .assertThat()
                .statusCode(401);
    }
    @Test
    void getMyPostWithoutTokenTest() {
        given()
                .queryParam("sort", "createdAt")
                .when()
                .get(URL)
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    void getMyPostWithoutParamsTest() {
        given()
                .spec(requestSpecification)
                .when()
                .get(URL)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }
    @Test
    void getMyPostWithDateOfPublicationByDefaultTest() {
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("sort", "createdAt")
                .when()
                .get(URL)
                .jsonPath();
        int id = response.get("data[1].id");
        assertThat(response.get("data[0].id"), lessThan(id));
    }
    @Test
    void getMyPostWithDateOfPublicationAscTest() {
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .when()
                .get(URL)
                .jsonPath();
        int id = response.get("data[1].id");
        assertThat(response.get("data[0].id"), lessThan(id));
    }
    @Test
    void getMyPostWithDateOfPublicationDescTest() {
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .when()
                .get(URL)
                .jsonPath();
        int id = response.get("data[1].id");
        assertThat(response.get("data[0].id"), greaterThan(id));
    }
    @Test
    void getMyNotExistedPageTest() {
        given()
                .spec(requestSpecification)
                .queryParam("sort", "createdAt")
                .queryParam("order", "")
                .queryParam("page", "-1")
                .when()
                .get(URL)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(400);
    }

    @Test
    void getNotMyPostWithoutParamsTest() {
        given()
                .spec(requestSpecification)
                .queryParam("owner" , "notMe")
                .when()
                .get(URL)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }
    @Test
    void getNotMyPostWithDateOfPublicationByDefaultTest() {
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("owner" , "notMe")
                .queryParam("sort", "createdAt")
                .when()
                .get(URL)
                .jsonPath();
        int id = response.get("data[1].id");
        assertThat(response.get("data[0].id"), lessThan(id));
    }
    @Test
    void getNotMyPostWithDateOfPublicationAscTest() {
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .when()
                .get(URL)
                .jsonPath();
        int id = response.get("data[1].id");
        assertThat(response.get("data[0].id"), lessThan(id));
    }
    @Test
    void getNotMyPostWithDateOfPublicationDescTest() {
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .when()
                .get(URL)
                .jsonPath();
        int id = response.get("data[1].id");
        assertThat(response.get("data[0].id"), greaterThan(id));
    }
    @Test
    void getNotMyNotExistedPageTest() {
        given()
                .spec(requestSpecification)
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "")
                .queryParam("page", "-1")
                .when()
                .get(URL)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(400);
    }

}
