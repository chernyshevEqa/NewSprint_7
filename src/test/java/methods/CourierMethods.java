package methods;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.Courier;
import static io.restassured.RestAssured.given;
import static models.Endpoints.CREATECOURIER;
import static models.Endpoints.LOGIN;

import io.restassured.response.Response;
import models.CourierCreds;
import models.CourierId;

public class CourierMethods  {

    @Step("создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(CREATECOURIER);
    }
    @Step("логин курьера")
    public Response loginCourier(CourierCreds creds) {
        return given()
                .contentType(ContentType.JSON)
                .body(creds)
                .when()
                .post(LOGIN);
    }
    @Step("удаление курьера")
    public Response deleteCourier(Integer id) {
        RequestSpecification request = given()
                .contentType(ContentType.JSON);

        // Отправляем запрос с или без id в URL
        Response response;
        if (id != null) {
            response = request
                    .when()
                    .delete("/api/v1/courier/" + id);
        } else {
            response = request
                    .when()
                    .delete("/api/v1/courier"); // без id в URL
        }
        return response;
    }

    @Step("получение id курьера")
    public int getCourierId(CourierCreds creds) {
        return given()
                .contentType(ContentType.JSON)
                .body(creds)
                .when()
                .post(LOGIN)
                .then().extract().as(CourierId.class).getId();
    }
}
