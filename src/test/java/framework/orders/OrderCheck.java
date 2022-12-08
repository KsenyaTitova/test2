package framework.orders;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.*;

public class OrderCheck {

    public int createdSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .body("track", greaterThan(0))
                .and()
                .statusCode(201)
                .extract()
                .path("track");
    }

    public void canceledSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }


    public String getListOrdersAllSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .body("orders", notNullValue())
                .and()
                .statusCode(200)
                .extract()
                .path("orders")
                .toString();
    }
}

