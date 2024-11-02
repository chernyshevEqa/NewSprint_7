package courierTests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import methods.CourierMethods;
import models.Courier;
import models.CourierCreds;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.Utils;

import static generators.CourierGenerator.randomCourier;
import static models.CourierCreds.credsFromCourier;
import static models.Errors.expectedErrorCourierNotFound;
import static models.Errors.expectedErrorNoAnyValue;

public class LoginCourierTests {



    private CourierMethods courierMethods;
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courierMethods = new CourierMethods();
    }

    @Test
    @Description("курьер может авторизоваться")
    public void loginTest() {
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        courierMethods.createCourier(courier);
        // получаем креды курьера
        CourierCreds creds = credsFromCourier(courier);
        // логинемся по полученным кредам
        Response login = courierMethods.loginCourier(creds);
        id = courierMethods.getCourierId(creds);
        //проверяем статус код
        Assert.assertEquals(200, login.getStatusCode());
    }

    @Test
    @Description("для авторизации нужно передать все обязательные поля")
    public void loginNoPasswordTest() {
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        courierMethods.createCourier(courier);
        // получаем креды курьера для логина и последующего удаления создданого курьера
        CourierCreds creds = credsFromCourier(courier);
        //логинимся правильными кредами
        Response login = courierMethods.loginCourier(creds);
        id = courierMethods.getCourierId(creds);
        CourierCreds credsLoginNull = new CourierCreds(null, "password");
        // логинемся по полученным кредам
        Response response = courierMethods.loginCourier(credsLoginNull);
        //проверяем статус код
        Assert.assertEquals(400, response.getStatusCode());
    }

    @Test
    @Description("система вернёт ошибку, если неправильно указать логин или пароль")
    public void getErrorWrongCredsTest() {
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        courierMethods.createCourier(courier);
        // получаем креды курьера для логина и последующего удаления создданого курьера
        CourierCreds creds = credsFromCourier(courier);
        //логинимся правильными кредами
        courierMethods.loginCourier(creds);
        id = courierMethods.getCourierId(creds);
        CourierCreds credsLoginNull = new CourierCreds(Utils.randomString(5), Utils.randomString(5));
        // логинемся по полученным кредам
        Response response = courierMethods.loginCourier(credsLoginNull);
        //проверяем статус код
        Assert.assertEquals(response.jsonPath().getString("message"), expectedErrorCourierNotFound);
    }

    @Test
    @Description("если какого-то поля нет, запрос возвращает ошибку")
    public void getErrorNoPasswordTest() {
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        courierMethods.createCourier(courier);
        // получаем креды курьера для логина и последующего удаления создданого курьера
        CourierCreds creds = credsFromCourier(courier);
        //логинимся правильными кредами
        Response login = courierMethods.loginCourier(creds);
        id = courierMethods.getCourierId(creds);
        CourierCreds credsLoginNull = new CourierCreds(null, "password");
        // логинемся по полученным кредам
        Response response = courierMethods.loginCourier(credsLoginNull);
        //проверяем статус код
        Assert.assertEquals(expectedErrorNoAnyValue, response.jsonPath().getString("message"));
    }

    @Test
    @Description("если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void getErrorNonExistUserTest() {

        CourierCreds credsLoginNull = new CourierCreds(Utils.randomString(6), Utils.randomString(6));
        // логинемся по полученным кредам
        Response response = courierMethods.loginCourier(credsLoginNull);
        //проверяем статус код
        Assert.assertEquals(expectedErrorCourierNotFound, response.jsonPath().getString("message"));
    }

    @Test
    @Description("успешный запрос возвращает id")
    public void getCourierIdTest() {
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        courierMethods.createCourier(courier);
        // получаем креды курьера
        CourierCreds creds = credsFromCourier(courier);
        // логинемся по полученным кредам
        Response login = courierMethods.loginCourier(creds);
        id = courierMethods.getCourierId(creds);
        //проверяем статус код
        Assert.assertNotNull(login.jsonPath().getString("id"));
    }

    @After
    public void tearDown() {
        courierMethods.deleteCourier(id);
    }
}
