package framework.courier;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import parameters.GeneralParameters;
import static io.restassured.RestAssured.given;

public class Courier {

    public ValidatableResponse createCourier (CourierRequest courier) {
        return   given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(GeneralParameters.getBASE_URI())
                .basePath(GeneralParameters.getAPI_PREFIX())
                .body(courier)
                .when()
                .post("/courier")
                .then().log().all();
    }

    public ValidatableResponse loginCourier (LoginCourierRequest login) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(GeneralParameters.getBASE_URI())
                .basePath(GeneralParameters.getAPI_PREFIX())
                .body(login)
                .when()
                .post("/courier/login")
                .then().log().all();
    }

    public ValidatableResponse deleteCourier (int courierId) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(GeneralParameters.getBASE_URI())
                .basePath(GeneralParameters.getAPI_PREFIX())
                .when()
                .delete("/courier/" + courierId)
                .then().log().all();
    }
}

