import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrdersApiClient {

    private final List<String> color;
    private final int code;

    @Before
    public void setUp() {
        RestAssured.baseURI= BaseURI.BASE_URI;
    }

    public OrdersApiClient( List<String> color, int code) {
        this.color = color;
        this.code = code;
    }

    @Parameterized.Parameters
    public static Object[][] getTextData() {

        List<String> colorBlack = List.of("BLACK");
        List<String> colorGray = List.of("GRAY");
        List<String> colorBlackGray = List.of("BLACK", "GRAY");
        List<String> colorNot = List.of();

        return new Object[][] {
                {colorBlack, 201},
                {colorGray, 201},
                {colorBlackGray, 201},
                {colorNot, 201},
        };
    }

    @Test
    @DisplayName("Проверка создания заказа (параметризированный тест)")
    @Description("Проверка производится на значениях по умолчанию, включает в себя проверку ответа track на ненулевое значение и кода ответа 201")
    @TmsLink("TestCase-112")
    @Issue("BUG-985")
    public void checkCreateOrdersTest() {

        Order order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", color);

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(BaseURI.ORDERS_ENDPOINT);

        response.then().assertThat().body("track", notNullValue());

        response.then().assertThat().statusCode(this.code);

    }

    @Test
    @DisplayName("Проверка списка заказов")
    @Description("Проверка производится на значениях по умолчанию, включает в себя проверку ответа track на ненулевое значение и кода ответа 201")
    @TmsLink("TestCase-112")
    @Issue("BUG-985")
    public void checkListOrdersTest() {

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(BaseURI.ORDERS_ENDPOINT);

        response.then().assertThat().body(notNullValue());

        response.then().assertThat().statusCode(200);

    }

}
