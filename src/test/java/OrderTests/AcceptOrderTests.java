package OrderTests;

import generators.OrderGenerator;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import methods.CourierMethods;
import methods.OrderMethods;
import models.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static generators.CourierGenerator.randomCourier;
import static models.CourierCreds.credsFromCourier;
import static models.Errors.*;
import static org.junit.Assert.assertTrue;

public class AcceptOrderTests {
    private OrderMethods orderMethods;
    private CourierMethods courierMethods;
    private CourierId id;
    private OrderResponce getOrderData;
    private GetTrackOrder getTrack;


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        orderMethods = new OrderMethods();
        courierMethods = new CourierMethods();
    }

    @Test
    @Description("успешный запрос возвращает ok: true")
    public void acceptOrder() {
        Courier courier = randomCourier();
        // создали курьера
        courierMethods.createCourier(courier);
        // получили id курьера
        CourierCreds creds = credsFromCourier(courier);
        Response login = courierMethods.loginCourier(creds);
        id = login.as(CourierId.class);
        // создали заказ
        OrderRequest orders = OrderGenerator.randomOrderTwoColors(List.of("black", "grey"));
        Response createResponce = orderMethods.createOrder(orders);
        //получили track заказ
        getTrack = createResponce.as(GetTrackOrder.class);
        // информацию по заказу
        getOrderData = orderMethods.getOrderByTrack(getTrack.getTrack());
        //назначаем заказ на курьера
        Response response = orderMethods.acceptOrder(getOrderData.data().getId(), id);
        //проверяем что вернулось true
        assertTrue(response.jsonPath().getBoolean("ok"));
    }

    @Test
    @Description("если не передать id курьера, запрос вернёт ошибку")
    public void acceptOrderNoCourierTest() {
        // создали заказ
        OrderRequest orders = OrderGenerator.randomOrderTwoColors(List.of("black", "grey"));
        Response createResponce = orderMethods.createOrder(orders);
        //получили track заказ
        getTrack = createResponce.as(GetTrackOrder.class);
        // получили id заказа
        getOrderData = orderMethods.getOrderByTrack(getTrack.getTrack());
        //назначаем заказ на курьера
        Response response = orderMethods.acceptOrder(getOrderData.data().getId(), null);
        //проверяем что вернулась ошибка
        Assert.assertEquals(response.jsonPath().getString("message"), expectedErrorNoData);
    }

    @Test
    @Description("если передать неверный id курьера, запрос вернёт ошибку")
    public void acceptOrderInvalidCourierIdTest() {
        //присваиваем курьеру неверный id
        CourierId invalidId = new CourierId();
        invalidId.setId(-1);
        // создали заказ
        OrderRequest orders = OrderGenerator.randomOrderTwoColors(List.of("black", "grey"));
        Response createResponce = orderMethods.createOrder(orders);
        //получили track заказ
        getTrack = createResponce.as(GetTrackOrder.class);
        // получили id заказа
        getOrderData = orderMethods.getOrderByTrack(getTrack.getTrack());
        //назначаем заказ на курьера
        Response response = orderMethods.acceptOrder(getOrderData.data().getId(), invalidId);
        //проверяем что вернулась ошибка
        Assert.assertEquals(response.jsonPath().getString("message"), expectedErrorNoCourier);
    }

    @Test
    @Description("если не передать номер заказа, запрос вернёт ошибку")
    public void acceptOrderNoOrderIdTest() {
        Courier courier = randomCourier();
        // создали курьера
        courierMethods.createCourier(courier);
        // получили id курьера
        CourierCreds creds = credsFromCourier(courier);
        Response login = courierMethods.loginCourier(creds);
        id = login.as(CourierId.class);
        // создали заказ
        OrderRequest orders = OrderGenerator.randomOrderTwoColors(List.of("black", "grey"));
        Response createResponce = orderMethods.createOrder(orders);
        //получили track заказ
        getTrack = createResponce.as(GetTrackOrder.class);
        // получили id заказа
        getOrderData = orderMethods.getOrderByTrack(getTrack.getTrack());
        //назначаем заказ на курьера
        Response response = orderMethods.acceptOrder(null, id);
        //проверяем что вернулась ошибка
        Assert.assertEquals(response.jsonPath().getString("message"), expectedErrorNoFound);
    }

    @Test
    @Description("если передать неверный номер заказа, запрос вернёт ошибку")
    public void acceptOrderInvalidOrderId() {
        Courier courier = randomCourier();
        // создали курьера
        courierMethods.createCourier(courier);
        // получили id курьера
        CourierCreds creds = credsFromCourier(courier);
        Response login = courierMethods.loginCourier(creds);
        id = login.as(CourierId.class);
        // создали заказ
        OrderRequest orders = OrderGenerator.randomOrderTwoColors(List.of("black", "grey"));
        Response createResponce = orderMethods.createOrder(orders);
        //получили track заказ
        getTrack = createResponce.as(GetTrackOrder.class);
        // получили id заказа
        getOrderData = orderMethods.getOrderByTrack(getTrack.getTrack());
        //назначаем заказ на курьера
        Response response = orderMethods.acceptOrder(-1, id);
        //проверяем что вернулась ошибка
        Assert.assertEquals(response.jsonPath().getString("message"), expectedErrorNotFoundOrderById);
    }

    @After
    public void tearDown() {
        if(id != null){
            courierMethods.deleteCourier(id.getId());
        }
    }
}
