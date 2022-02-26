import classes.BaseURI;
import classes.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class createCourierTest {

    ArrayList<Courier> courierList = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssured.baseURI= BaseURI.BASE_URI;
    }

    @Test
    @DisplayName("Проверка того, что курьера можно создать")
    @Description("Проверка производится на произвольных значениях login, password, name, включает в себя проверку ответа на OK true и кода ответа 201")
    @TmsLink("TestCase-112")
    @Issue("BUG-985")
    public void checkSuccessCreateCourierTest() {

        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(BaseURI.COURIER_ENDPOINT);

        if (response.statusCode() == 201) {
            courierList.add(courier);
        }

        response.then().assertThat().body("ok", equalTo(true));

        response.then().assertThat().statusCode(201);

    }

    @Test
    @DisplayName("Проверка того, что нельзя создать двух одинаковых курьеров")
    @Description("Проверка производится на произвольных знаечениях, включает в себя проверку code, message  и кода ответа 409")
    @TmsLink("TestCase-112")
    @Issue("BUG-985")
    public void checkFaultCreateDoubleCourierTest() {

        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierName = RandomStringUtils.randomAlphabetic(10);

        courierList.add(new Courier(courierLogin, courierPassword, courierName));
        courierList.add(new Courier(courierLogin, courierPassword, courierName));

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(courierList.get(0))
                .when()
                .post(BaseURI.COURIER_ENDPOINT);

        if (response.statusCode() == 201) {

            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(courierList.get(1))
                    .when()
                    .post(BaseURI.COURIER_ENDPOINT);

        }

        response.then().assertThat().body("code", equalTo(409));
        response.then().assertThat().body("message", equalToIgnoringCase("Этот логин уже используется. Попробуйте другой."));
        response.then().assertThat().statusCode(409);

    }

    @Test
    @DisplayName("Проверка того, что нельзя создать двух одинаковых курьеров")
    @Description("Проверка производится на знаечениях по умолчанию, включает в себя проверку code, message  и кода ответа 409")
    @TmsLink("TestCase-112")
    @Issue("BUG-985")
    public void checkFaultCreateCourierOnDefaultDataTest() {

        Courier courier = new Courier(Courier.DEFAULT_LOGIN, Courier.DEFAULT_PASSWORD, "");

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(BaseURI.COURIER_ENDPOINT);

        if (response.statusCode() == 201) {
            courierList.add(courier);
        }

        response.then().assertThat().body("code", equalTo(409));
        response.then().assertThat().body("message", equalToIgnoringCase("Этот логин уже используется. Попробуйте другой."));
        response.then().assertThat().statusCode(409);

    }

    @Test
    @DisplayName("Проверка чтобы создать курьера, нужно передать в ручку все обязательные поля")
    @Description("Проверка производится без логина, включает в себя проверку message и кода ответа 400")
    @TmsLink("TestCase-112")
    @Issue("BUG-985")
    public void checkFaultCreateCourierWitchNoLoginDataTest() {

        Courier courier = new Courier("", Courier.DEFAULT_PASSWORD);

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(BaseURI.COURIER_ENDPOINT);

        if (response.statusCode() == 201) {
            courierList.add(courier);
        }

        response.then().assertThat().body("message", equalToIgnoringCase("Недостаточно данных для создания учетной записи"));
        response.then().assertThat().statusCode(400);

    }

    @Test
    @DisplayName("Проверка чтобы создать курьера, нужно передать в ручку все обязательные поля")
    @Description("Проверка производится без пароля, включает в себя проверку message и кода ответа 400")
    @TmsLink("TestCase-112")
    @Issue("BUG-985")
    public void checkFaultCreateCourierWitchNoPasswordDataTest() {

        Courier courier = new Courier(RandomStringUtils.randomAlphabetic(10), "");

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(BaseURI.COURIER_ENDPOINT);

        if (response.statusCode() == 201) {
            courierList.add(courier);
        }

        response.then().assertThat().body("message", equalToIgnoringCase("Недостаточно данных для создания учетной записи"));
        response.then().assertThat().statusCode(400);

    }

    public void checkSuccessDeleteCourierTest() {

        Response responsePost = null;
        Response responseDelete = null;
        JsonPath jsonPathValidator = null;

        for (int i = 0; i < courierList.size(); i++) {

            responsePost = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(courierList.get(i))
                    .when()
                    .post(BaseURI.LOGIN_ENDPOINT);

            if (responsePost.statusCode() == 200) {
                jsonPathValidator = responsePost.jsonPath();

                courierList.get(i).setId(jsonPathValidator.get("id").toString());

                responseDelete =  given()
                        .header("Content-type", "application/json")
                        .delete(BaseURI.COURIER_ENDPOINT + "/" + courierList.get(i).getId());

            }

            responseDelete.then().assertThat().body("ok", equalTo(true));

            responseDelete.then().assertThat().statusCode(200);

        }

    }

    @After
    public void deleteCourier() {
        if (!courierList.isEmpty()) {
            checkSuccessDeleteCourierTest();
        }
    }

}
