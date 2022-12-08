package courierTests;

import framework.courier.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

public class CourierTest {

    private final CourierGenerator generator= new CourierGenerator();
    private final Courier courier = new Courier();
    private final CourierCheck  courierCheck = new CourierCheck();
    private int courierId;
    private String cleanUpAfterYourself = "Удалять нечего";

    @Test
    @DisplayName("Создание нового курьера со всеми полями")
    public void createANewCourierReturnTrue() {
        CourierRequest newCourier = generator.generic();
        ValidatableResponse response = courier.createCourier(newCourier);
        cleanUpAfterYourself = courierCheck.createdSuccessfully(response);
    }

    @Test
    @DisplayName("Создание курьера-дубля, который уже существует в системе")
    public void createADoubleCourierReturnCode409() {
        CourierRequest newCourier = generator.generic();
        ValidatableResponse response = courier.createCourier(newCourier);
        cleanUpAfterYourself = courierCheck.createdSuccessfully(response);
        CourierRequest doubleCourier = generator.generic();
        ValidatableResponse doubleResponse = courier.createCourier(doubleCourier);
        courierCheck.failedMessage(doubleResponse, "Этот логин уже используется. Попробуйте другой.", 409);
    }

    @Test
    @DisplayName("Создание курьера без обязательного параметра login")
    public void createANewCourierWithoutLoginReturnCode400 () {
        CourierRequest newCourier = generator.generic();
        newCourier.setLogin(null);
        ValidatableResponse response = courier.createCourier(newCourier);
        courierCheck.failedMessage(response, "Недостаточно данных для создания учетной записи", 400);
    }

    @Test
    @DisplayName("Создание курьера без обязательного параметра password")
    public void createANewCourierWithoutPasswordReturnCode400 () {
        CourierRequest newCourier = generator.generic();
        newCourier.setPassword(null);
        ValidatableResponse response = courier.createCourier(newCourier);
        courierCheck.failedMessage(response, "Недостаточно данных для создания учетной записи", 400);
    }

    @Test
    @DisplayName("Создание курьера со всеми обязательными полями")
    public void createANewCourierWithoutFirstNameReturnTrue () {
        CourierRequest newCourier = generator.generic();
        newCourier.setFirstName(null);
        ValidatableResponse response = courier.createCourier(newCourier);
        cleanUpAfterYourself = courierCheck.createdSuccessfully(response);
    }

    @Test
    @DisplayName("Проверка авторизации курьера c правильным логином/паролем")
    public void checkLoginCourierCorrectLoginAndCorrectPasswordSuccessfully() {
        CourierRequest newCourier = generator.generic();
        ValidatableResponse response = courier.createCourier(newCourier);
        cleanUpAfterYourself = courierCheck.createdSuccessfully(response);
        LoginCourierRequest login = LoginCourierRequest.from(newCourier);
        ValidatableResponse loginResponse = courier.loginCourier(login);
        courierCheck.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Проверка авторизации курьера c неправильным логином и правильным паролем")
    public void checkLoginCourierIncorrectLoginAndCorrectPasswordReturnCodeStatus404() {
        CourierRequest newCourier = generator.generic();
        ValidatableResponse response = courier.createCourier(newCourier);
        cleanUpAfterYourself = courierCheck.createdSuccessfully(response);
        LoginCourierRequest login = LoginCourierRequest.from(newCourier);
        login.setLogin(RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse loginResponse = courier.loginCourier(login);
        courierCheck.failedMessage(loginResponse, "Учетная запись не найдена", 404);
    }

    @Test
    @DisplayName("Проверка авторизации курьера c правильным логином и неправильным паролем")
    public void checkLoginCourierCorrectLoginAndIncorrectPasswordReturnCodeStatus404() {
        CourierRequest newCourier = generator.generic();
        ValidatableResponse response = courier.createCourier(newCourier);
        cleanUpAfterYourself = courierCheck.createdSuccessfully(response);
        LoginCourierRequest login = LoginCourierRequest.from(newCourier);
        login.setPassword(RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse loginResponse = courier.loginCourier(login);
        courierCheck.failedMessage(loginResponse, "Учетная запись не найдена", 404);
    }

    @Test
    @DisplayName("Проверка авторизации курьера без логина")
    public void checkLoginCourierWithoutLoginReturnCodeStatus400() {
        CourierRequest newCourier = generator.generic();
        ValidatableResponse response = courier.createCourier(newCourier);
        cleanUpAfterYourself = courierCheck.createdSuccessfully(response);
        LoginCourierRequest login = LoginCourierRequest.from(newCourier);
        login.setLogin(null);
        ValidatableResponse loginResponse = courier.loginCourier(login);
        courierCheck.failedMessage(loginResponse, "Недостаточно данных для входа", 400);
    }

    @Test
    @DisplayName("Проверка авторизации курьера без пароля")
    public void checkLoginCourierWithoutPasswordReturnCodeStatus400() {
        CourierRequest newCourier = generator.generic();
        ValidatableResponse response = courier.createCourier(newCourier);
        cleanUpAfterYourself = courierCheck.createdSuccessfully(response);
        LoginCourierRequest login = LoginCourierRequest.from(newCourier);
        login.setPassword(null);
        ValidatableResponse loginResponse = courier.loginCourier(login);
        courierCheck.failedMessage(loginResponse, "Недостаточно данных для входа", 400);
    }

    @After
    public void deleteCourier() {
        if (cleanUpAfterYourself.equals("Нужно удалить!")) {
            CourierRequest newCourier = generator.generic();
            LoginCourierRequest login = LoginCourierRequest.from(newCourier);
            ValidatableResponse loginResponse = courier.loginCourier(login);
            courierId = courierCheck.loggedInSuccessfully(loginResponse);
            ValidatableResponse response = courier.deleteCourier(courierId);
            courierCheck.deletedSuccessfully(response);
        }
    }
}