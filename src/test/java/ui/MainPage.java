package ui;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    SelenideElement logInBtn = $x("//a[contains(@class,'navbar__auth_login')]");

    public MainPage openMainPage() {
        open("https://stepik.org/");
        return this;
    }

    public AuthPage clickOnLogIn() {
        logInBtn.click();
        return new AuthPage();
    }
}
