package api;

import api.pojo.AuthorizationData;
import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static tests.AuthorizationTests.*;

public class AuthService {

    private final String AUTH_URL = "https://stepik.org/api/users/login";
    private final String REFERER = "https://stepik.org";
    private final String AUTHORITY = "stepik.org";
    private final String ORIGIN = "https://stepik.org/catalog?auth=login";

    public Map<String, String> getAuthTokens(Set<Cookie> seleniumCookie) {

        AuthorizationData authorizationData = new AuthorizationData(EMAIL, PASSWORD);
        List<io.restassured.http.Cookie> restAssuredCookies = getRestAssuredCookiesBySeleniumCookies(seleniumCookie);

        Map<String, String> cookies = given()
                .contentType("application/json; charset=UTF-8")
                .cookies(new Cookies(restAssuredCookies))
                .header("Referer", REFERER)
                .header("Origin", ORIGIN)
                .header("authority", AUTHORITY)
                .header("X-Csrftoken", getCSRFHeaderValue(seleniumCookie))
                .body(authorizationData)
                .when()
                .post(AUTH_URL)
                .then()
                .log().all()
                .extract().cookies();

        return cookies;
    }

    private List<io.restassured.http.Cookie> getRestAssuredCookiesBySeleniumCookies(Set<Cookie> seleniumCookie) {
        List<io.restassured.http.Cookie> raCookies = new ArrayList<>();
        for (Cookie cookie : seleniumCookie) {
            io.restassured.http.Cookie tempCookie = new io.restassured.http.Cookie
                    .Builder(cookie.getName(), cookie.getValue())
                    .setDomain(cookie.getDomain())
                    .setPath("/")
                    .build();
            raCookies.add(tempCookie);
        }
        return raCookies;
    }

    private String getCSRFHeaderValue(Set<Cookie> seleniumCookie) {
        for (Cookie cookie : seleniumCookie) {
           if (cookie.getName().equals("csrftoken")) {
               return cookie.getValue();
           }
        }
        return "";
    }

}
