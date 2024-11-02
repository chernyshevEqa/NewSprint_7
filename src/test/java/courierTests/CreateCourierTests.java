package courierTests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import methods.CourierMethods;
import models.Courier;
import models.CourierCreds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generators.CourierGenerator.randomCourier;
import static generators.CourierGenerator.randomCourierNoPassword;
import static models.CourierCreds.credsFromCourier;
import static models.Errors.expectedErrorMessageExistLogin;
import static models.Errors.expectedErrorMessageForCreateCourier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTests {



    private CourierMethods courierMethods;
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courierMethods = new CourierMethods();
    }

    @Test
    @Description("курьера можно создать")
    public void createCourierTest() {
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        Response response = courierMethods.createCourier(courier);
        // проверяем что курьер создан
        assertEquals("Неверный статус код", 201, response.getStatusCode());
        // получаем креды курьера
        CourierCreds creds = credsFromCourier(courier);
        // логинемся по полученным кредам
        Response login = courierMethods.loginCourier(creds);
        id = courierMethods.getCourierId(creds);
        // проверяем что залогинились
        assertEquals("Неверный статус код", 200, login.getStatusCode());
    }

    @Test
    @Description("нельзя создать двух одинаковых курьеров")
    public void createTwoTheSameCourierTest() {
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        Response response = courierMethods.createCourier(courier);
        CourierCreds creds = credsFromCourier(courier);
        id = courierMethods.getCourierId(creds);
        //пытаем зарегистрировать курьера с теме же login and password
        Response tryToCreateOneCourier = courierMethods.createCourier(courier);
        // получаем тест сообщение ошибки
        String error = tryToCreateOneCourier.jsonPath().get("message");
        // проверяем статус код
        assertEquals("Нельзя создать двух одинкаковых курьеров", 409, tryToCreateOneCourier.getStatusCode());
    }

    @Test
    @Description("чтобы создать курьера, нужно передать в ручку все обязательные поля, так же проверяет кейс " +
            "что ошибка отображается")
    public void createCourierNoPasswordTest() {
        Courier courier = randomCourierNoPassword();
        //пытаем зарегать курьера без перачи пароля
        Response response = courierMethods.createCourier(courier);
        //получаем текст ошибки
        String error = response.jsonPath().getString("message");
        //проверяем тест ошибка
        assertEquals(expectedErrorMessageForCreateCourier, error);
    }

    @Test
    @Description("запрос возвращает правильный код ответа")
    public void successResponceReturnsTrueTest() {
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        Response response = courierMethods.createCourier(courier);
        // проверяем что курьер создан
        assertTrue(response.jsonPath().getBoolean("ok"));
    }

    @Test
    @Description("если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void returnsErrorCreateTwoCouriersTest   () {
        // создаём курьера
        Courier courier = randomCourier();
        // Регистрируем нового курьера
        Response response = courierMethods.createCourier(courier);
        CourierCreds creds = credsFromCourier(courier);
        id = courierMethods.getCourierId(creds);
        //пытаем зарегистрировать курьера с теме же login and password
        Response tryToCreateOneCourier = courierMethods.createCourier(courier);
        // получаем тест сообщение ошибки
        String error = tryToCreateOneCourier.jsonPath().get("message");
        // проверяем что тест ошибки совпадает с ожидаемым тестом
        assertEquals(expectedErrorMessageExistLogin, error);
    }

    @After
    public void tearDown() {
        courierMethods.deleteCourier(id);
    }
}
