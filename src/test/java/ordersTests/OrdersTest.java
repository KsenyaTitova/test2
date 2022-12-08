package ordersTests;

import framework.orders.Order;
import framework.orders.OrderCheck;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import framework.orders.CreateOrderRequest;

@RunWith(Parameterized.class)
public class OrdersTest {

    private final String testText;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
    private int track;
    private final Order order = new Order();
    private final OrderCheck orderCheck = new OrderCheck();

    public OrdersTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color, String testText) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
        this.testText = testText;
    }

    @Parameterized.Parameters(name = "{index} - scooter color: {9}")
    public static Object[][] getData() {
        return new Object[][]{
                {"Ксения", "Титова", "Ленина", "Ленина", "+79999999999", 1, "05.12.2022", "Hello", new String[]{"BLACK", "GREY"}, "Черный и серый"},
                {"Иван", "Иванов", "Иванова", "Охотный ряд", "+79111111111", 2, "06.12.2022", "Good", new String[]{"BLACK"}, "Черный"},
                {"Petr", "Petrov-Ivanov", "Pushkina", "Черкизовская", "+79000000000", 3, "01.12.2022", "Hello", new String[]{"GREY"}, "Серый"},
                {"Маша", "Сидорова", "Красная площадь, д.1", "Охотный ряд", "+79111111122", 4, "01.01.2023", "Спасибо", null, "Любой"},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder() {
        CreateOrderRequest newOrder = new CreateOrderRequest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = order.createOrder(newOrder);
        track = orderCheck.createdSuccessfully(response);
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void gettingAListOfOrders() {
        ValidatableResponse responseGetListOrders = order.getListOrders();
        orderCheck.getListOrdersAllSuccessfully(responseGetListOrders);
    }

    @After
    public void cancelOrder() {
        if (track > 0) {
            ValidatableResponse response = order.cancelOrder(track);
            orderCheck.canceledSuccessfully(response);
        }
    }
}
