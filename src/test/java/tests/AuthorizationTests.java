package tests;

import api.pojo.AuthorizationData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;


import org.junit.jupiter.api.Test;
import ui.annotations.InjectAuthDataAnnotation;
import ui.annotations.InjectMainPageAnnotation;
import ui.pages.MainPage;
import ui.annotations.BrowserRunTypes;

import javax.inject.Inject;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.*;


@InjectMainPageAnnotation
@BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME, isRemote = false)
public class AuthorizationTests {

    @Inject
    protected MainPage mainPage;
    public static final String EMAIL = "gann.semmi@mail.ru";
    public static final String PASSWORD = "S12345678";


    @Test
    @Tag("Auth")
    @DisplayName("Authorization with login and password")
    public void successfulTest(@InjectAuthDataAnnotation AuthorizationData authorizationData) {

        boolean isProfile = mainPage.openMainPage()
                .clickOnLogIn()
                .fillEmailAndPassword(authorizationData)
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
