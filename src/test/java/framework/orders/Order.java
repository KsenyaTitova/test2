package framework.orders;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import parameters.GeneralParameters;
import static io.restassured.RestAssured.given;

public class Order {

    public ValidatableResponse createOrder (CreateOrderRequest order) {
        return   given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(GeneralParameters.getBASE_URI())
                .basePath(GeneralParameters.getAPI_PREFIX())
                .body(order)
                .when()
                .post("/orders")
                .then().log().all();
    }

    public ValidatableResponse cancelOrder (int track) {
        return   given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(GeneralParameters.getBASE_URI())
                .basePath(GeneralParameters.getAPI_PREFIX())
                .queryParam("track", track)
                .when()
                .put("/orders/cancel")
                .then().log().all();
    }

    public ValidatableResponse getListOrders () {
        return   given().log().all()
                .baseUri(GeneralParameters.getBASE_URI())
                .basePath(GeneralParameters.getAPI_PREFIX())
                .when()
                .get("/orders")
                .then().log().all();
    }
}

