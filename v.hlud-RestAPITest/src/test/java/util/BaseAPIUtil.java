package util;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseAPIUtil {

    public static RequestSpecification getRequest() {
        return given()
                .contentType(ContentType.JSON);
    }
}
