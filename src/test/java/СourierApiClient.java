import io.qameta.allure.Step;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class СourierApiClient {

    @Step("Авторизация курьера")
    public Response courierLogin(Courier courier, String endpiont) {

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(endpiont);

    }

    @Step("Удаление курьера")
    public void deleteCourier(ArrayList<Courier> courierList, String loginEndpiont, String courierEndpoint) {

        Response response = null;
        JsonPath jsonPathValidator = null;

        for (int i = 0; i < courierList.size(); i++) {

            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(courierList.get(i))
                    .when()
                    .post(loginEndpiont);

            if (response.statusCode() == 200) {

                jsonPathValidator = response.jsonPath();

                courierList.get(i).setId(jsonPathValidator.get("id").toString());

                given()
                        .header("Content-type", "application/json")
                        .delete(courierEndpoint + "/" + courierList.get(i).getId());

            }

        }

    }

}
