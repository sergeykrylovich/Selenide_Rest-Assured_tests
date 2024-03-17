package tests;

import api.AuthService;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import ui.MainPage;
import ui.annotations.BrowserRunTypes;

import java.util.Map;
import java.util.Set;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.*;


@BrowserRunTypes
public class AuthorizationTests {

    protected MainPage mainPage;
    public static final String EMAIL = "gann.semmi@mail.ru";
    public static final String PASSWORD = "S12345678";

    @BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME, isRemote = false)
    @Test
    @Tag("UI")
    @DisplayName("Authorization with login and password")
    public void successfulTest() {

        mainPage = new MainPage();
        boolean isProfile = mainPage.openMainPage()
                .clickOnLogIn()
                .fillEmailAndPassword(EMAIL, PASSWORD)
                .submitCredentialsAndLogin()
                .imgProfileIsExist();

        assertThat(isProfile).isTrue();

    }
    @BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME, isRemote = false)
    @Test
    @Tag("UI")
    @DisplayName("Authorization through api with login and password")
    public void authThroughApiTest() {
        mainPage = new MainPage();
        AuthService authService = new AuthService();
        mainPage.openMainPage();
        Set<Cookie> cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        Map<String, String> responseCookies = authService.getAuthTokens(cookies);
        mainPage.setCookies(responseCookies);
        Selenide.refresh();

        assertThat(mainPage.imgProfileIsExist()).isTrue();
    }
}
