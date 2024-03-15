package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import ui.MainPage;
import ui.annotations.BrowserRunTypes;
import ui.browser.BrowserTypeProcessing;
import ui.browser.SystemProperties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.*;


@BrowserRunTypes
public class AuthorizationTests {

    MainPage mainPage;
    private static final String email = "dantist4444@mail.ru";
    private static final String password = "Sergey1855796";

    @BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME, isRemote = false)
    @Test
    @Tag("UI")
    @DisplayName("Authorization with login and password")
    public void successfulTest() {

        mainPage = new MainPage();
        boolean isProfile = mainPage.openMainPage()
                .clickOnLogIn()
                .fillEmailAndPassword(email, password)
                .submitCredentialsAndLogin()
                .imgProfileIsExist();

        assertThat(isProfile).isTrue();

    }
}
