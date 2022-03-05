import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrdersApiClient {

    @Step("Список заказов")
    public Response listOrders() {

        return  given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(BaseURI.ORDERS_ENDPOINT);

    }

    @Step("Создание заказа")
    public Response createOrder(Order order) {

        return  given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(BaseURI.ORDERS_ENDPOINT);

    }

}
