package courierTests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import methods.CourierMethods;
import models.Courier;
import models.CourierCreds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static generators.CourierGenerator.randomCourier;
import static models.CourierCreds.credsFromCourier;
import static models.Errors.expectedErrorNoFound;
import static models.Errors.expectedErrorNotFoundCourier;

public class DeleteCourierTests {

    private CourierMethods courierMethods;
    private int id;


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courierMethods = new CourierMethods();
    }

    @Test
    @Description("успешный запрос возвращает ok: true")
    public void returnsTrueTest() {
        CourierMethods methods = new CourierMethods();
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        courierMethods.createCourier(courier);
        // получаем креды курьера
        CourierCreds creds = credsFromCourier(courier);

        id = courierMethods.getCourierId(creds);

        Response response = methods.deleteCourier(id);
        Assert.assertTrue("курьера с таким id нет", response.jsonPath().getBoolean("ok"));
    }

    @Test
    @Description("неуспешный запрос возвращает соответствующую ошибку")
    public void getErrorNotFoundCourierTest() {
        Response response = courierMethods.deleteCourier(0);
        Assert.assertEquals(expectedErrorNotFoundCourier, response.jsonPath().getString("message"));
    }

    @Test
    @Description("если отправить запрос без id, вернётся ошибка")
    public void getErrorNoIdTest() {
        Response response = courierMethods.deleteCourier(null);
        Assert.assertEquals( expectedErrorNoFound, response.jsonPath().getString("message"));
    }

    @Test
    @Description("если отправить запрос с несуществующим id, вернётся ошибка")
    public void getErrorIncorrectIdTest() {
        Response response = courierMethods.deleteCourier(-1);
        Assert.assertEquals(expectedErrorNotFoundCourier, response.jsonPath().getString("message"));
    }
}
