package model.api;

import constants.ApiUrls;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.body.LoginRequestBody;
import model.body.RegisterRequestBody;

import static io.restassured.RestAssured.given;

public class UserApi {
    private static final String registerUrl = ApiUrls.STELLAR_BURGERS_URL + ApiUrls.REGISTER_HANDLE;

    private static final String loginUrl = ApiUrls.STELLAR_BURGERS_URL + ApiUrls.LOGIN_HANDLE;

    private static final String deleteUrl = ApiUrls.STELLAR_BURGERS_URL + ApiUrls.USER_HANDLE;


    @Step("Регистрация пользователя")
    public static Response register(RegisterRequestBody registerRequestBody) {
        return given()
                .header("Content-type", "application/json")
                .body(registerRequestBody)
                .post(registerUrl);
    }

    @Step("Логин пользователя")
    public static Response login(LoginRequestBody loginRequestBody) {
        return given()
                .header("Content-type", "application/json")
                .body(loginRequestBody)
                .post(loginUrl);
    }

    @Step("Удаление пользователя")
    public static Response delete(String token) {
        return given()
                .auth()
                .oauth2(token)
                .header("Content-type", "application/json")
                .delete(deleteUrl);
    }

    @Step("Попытка логина пользователя, при усехе - удаление пользователя")
    public static Response tryLoginAndDelete(LoginRequestBody loginRequestBody) {
        String token = loginAndGetToken(loginRequestBody);

        if (token != null) {
            return UserApi.delete(token);
        } else {
            return null;
        }
    }

    @Step("Логин и получение токена")
    public static String loginAndGetToken(LoginRequestBody loginRequestBody) {
        String token = login(loginRequestBody)
                .path("accessToken");
        return trimToken(token);
    }

    @Step("Отсечение 'BEARER ' из начала токена")
    public static String trimToken(String token) {
        if (token == null) {
            return null;
        } else {
            return token.substring(7);
        }
    }

}
