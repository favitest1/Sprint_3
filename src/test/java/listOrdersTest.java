import classes.BaseURI;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class listOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI= BaseURI.BASE_URI;
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
