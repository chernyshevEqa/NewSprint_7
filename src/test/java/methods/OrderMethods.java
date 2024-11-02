package methods;

import generators.OrderGenerator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.*;

import java.util.List;

import static generators.CourierGenerator.randomCourier;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static models.CourierCreds.credsFromCourier;
import static models.Endpoints.*;
import static org.junit.Assert.assertTrue;

public class OrderMethods {

    public Response createOrder(OrderRequest orders) {
        return given()
                .contentType(ContentType.JSON)
                .body(orders)
                .when()
                .post(ORDERS);
    }

    public OrdersList getOrdersList() {
       Response response =  given()
                .contentType(ContentType.JSON)
                .when()
                .get(ORDERS);
       return response.as(OrdersList.class);
    }

    public OrderResponce getOrderByTrack(Integer trackOrder) {
        RequestSpecification request = given()
                .contentType(ContentType.JSON);
        //проверяем что id курьера не null
        OrderResponce response;
        if (trackOrder != null) {
            request.queryParam("t", trackOrder);
        }

        response = request.when()
                .get(GETORDERBYTRACK)
                .then().extract().as(OrderResponce.class);
        return response;
    }


    public Response acceptOrder(Integer orderId, CourierId courierId) {
        RequestSpecification request = given()
                .contentType(ContentType.JSON);
        //проверяем что id курьера не null
        if (courierId != null) {
            request.queryParam("courierId", courierId.getId());
        }
        Response response;
        if(orderId != null){
            response = request
                    .when()
                    .put(ACCEPTORDER + orderId);
        } else {
            response = request
                    .when()
                    .put(ACCEPTORDER);
        }
        return response;
    }

    public Response cancelOrder(GetTrackOrder trackOrder) {
        Response re =    given()
                .contentType(ContentType.JSON)
                .body(trackOrder)
                .log().all()
                .when()
                .put(CANCELORDER);
        return re;
    }

    public Response finishOrder(int idOrder) {
        Response response =    given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .put(FINISHORDER + idOrder);
        return response;
    }



}
