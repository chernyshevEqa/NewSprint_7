package OrderTests;

import generators.OrderGenerator;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import methods.OrderMethods;
import models.GetTrackOrder;
import models.OrderRequest;
import models.OrderResponce;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static models.Endpoints.GETORDERBYTRACK;
import static models.Errors.expectedErrorNoData;
import static models.Errors.expectedErrorNotFoundOrder;


public class GetOrderByIdTests {

    private OrderMethods orderMethods;
    private GetTrackOrder getTrack;
    private OrderResponce orderResponce;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        orderMethods = new OrderMethods();
    }

    @Test
    @Description("успешный запрос возвращает объект с заказом")
    public void successfulResponseGetOrderObject() {
        //создали заказ
        OrderRequest orders = OrderGenerator.randomOrderTwoColors(List.of("black", "grey"));
        Response createResponce = orderMethods.createOrder(orders);
        //получили track заказа
        getTrack = createResponce.as(GetTrackOrder.class);
        //получили информацию по заказу
        orderResponce = orderMethods.getOrderByTrack(getTrack.getTrack());
        //проверяем что запрос вернул обьект
        Assert.assertNotNull(orderResponce);

    }

    @Test
    @Description("запрос без номера заказа возвращает ошибку")
    public void getErrorRequestNoIdOrder() {
        //создали заказ
        OrderRequest orders = OrderGenerator.randomOrderTwoColors(List.of("black", "grey"));
        orderMethods.createOrder(orders);
        //получили информацию по заказу
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("t", "")
                .when()
                .get(GETORDERBYTRACK);
        Assert.assertEquals(expectedErrorNoData, response.jsonPath().getString("message"));
    }

    @Test
    @Description("запрос с несуществующим заказом возвращает ошибку")
    public void getErrorRequestNonExistOrder() {
        Random random = new Random();
        int randomInt = 10001 + random.nextInt(90000);
        //создали заказ
        OrderRequest orders = OrderGenerator.randomOrderTwoColors(List.of("black", "grey"));
        orderMethods.createOrder(orders);
        //получили информацию по заказу
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("t", randomInt)
                .when()
                .get(GETORDERBYTRACK);
        Assert.assertEquals(expectedErrorNotFoundOrder, response.jsonPath().getString("message"));
    }

}
