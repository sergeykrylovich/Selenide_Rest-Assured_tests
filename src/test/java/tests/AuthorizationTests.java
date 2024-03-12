package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideConfig;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTests {

    @Test
    public void successfulTest() throws InterruptedException {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        open("https://stepik.org/");
        $x("//a[contains(@class,'navbar__auth_login')]").click();
        Thread.sleep(3000);
    }
}
