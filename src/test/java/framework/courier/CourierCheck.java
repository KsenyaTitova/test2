package framework.courier;


import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;

public class CourierCheck {

    public String createdSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("ok",equalTo(true))
                .and()
                .statusCode(201);
        return "Нужно удалить!";
    }

    public int loggedInSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .body("id", greaterThan(0))
                .and()
                .statusCode(200)
                .extract()
                .path("id");
    }

    public void failedMessage(ValidatableResponse response, String message, int statusCode) {
        response.assertThat()
                .body("message", equalTo(message))
                .and()
                .statusCode(statusCode);
    }

    public void deletedSuccessfully (ValidatableResponse response) {
        response.assertThat()
                .body("ok",equalTo(true))
                .and()
                .statusCode(200);
    }
}

