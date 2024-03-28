package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pages.MainPage;
import ui.annotations.BrowserRunTypes;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.*;


@BrowserRunTypes
public class AuthorizationTests {

    protected MainPage mainPage;
    public static final String EMAIL = "gann.semmi@mail.ru";
    public static final String PASSWORD = "S12345678";

    @BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME, isRemote = true)
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
   // @BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME, isRemote = false)
    @Test
    //@Tag("UI")
    @DisplayName("Authorization through api with login and password")
    public void authThroughApiTest() {
        mainPage = new MainPage();
        mainPage.openMainPage().authorizeThroughAPI();
       /* Set<Cookie> cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        Map<String, String> responseCookies = authService.getAuthTokens(cookies);
        mainPage.setCookies(responseCookies);
        System.out.println(responseCookies);
        Selenide.refresh();
        System.out.println(WebDriverRunner.getWebDriver().manage().getCookies());*/


        assertThat(mainPage.imgProfileIsExist()).isTrue();
    }
}
