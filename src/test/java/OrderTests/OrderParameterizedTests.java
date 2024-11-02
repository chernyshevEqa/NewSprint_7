package OrderTests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static models.Endpoints.ORDERS;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
@RunWith(Parameterized.class)
public class OrderParameterizedTests {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;


    public OrderParameterizedTests(String firstName, String lastName, String address, int metroStation, String phone, int rentTime,
                                   String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }



    @Parameterized.Parameters
    public static List<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                {"Evgeniy", "Petrov", "Lenin street 13 apt.", 26, "+7 900 765 34 55", 5, "2024-10-10",
                        "call before coming", List.of("black")},
                {"Ivan", "Ivanov", "Pobeda street 45 apt.", 215, "+7 900 345 55 12", 4, "2024-05-05",
                        "call before coming", List.of("white")},
                {"Vasily", "Vasuliev", "Mujestva street 67 apt.", 4, "+7 900 845 98 38", 3, "2024-12-12",
                        "call before coming", List.of("black", "white")},
                {"Katya", "Popova", "srednya street 105 apt.", 3, "+7 900 328 46 92", 2, "2024-03-03",
                        "call before coming", Collections.emptyList()}
        });
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Description("первые два теста проверяют что можно создать заказ указав один из цветов" +
            "третий тест проверяет что можно не указывать тест" +
            "четвёртый тест проверяет что тело ответа позвращает поле track")
    public void parameterizedTest() {
        Map response = given()
                .contentType(ContentType.JSON)
                .body(this)
                .when()
                .post(ORDERS)
                .then()
                .statusCode(201)
                .extract()
                .body()
                .as(Map.class);
        assertTrue("Ответ должен содержать поле track", response.containsKey("track"));
    }
}
