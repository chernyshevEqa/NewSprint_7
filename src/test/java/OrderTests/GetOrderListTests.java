package OrderTests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import methods.OrderMethods;
import models.OrdersList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GetOrderListTests {
    private OrderMethods orderMethods;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        orderMethods = new OrderMethods();
    }

    @Test
    @Description("проверяем что список заказов не пусто")
    public void orderListNoNull() {
        OrdersList list = orderMethods.getOrdersList();
        Assert.assertNotNull(list);
    }
}
