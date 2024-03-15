package ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private final SelenideElement logInBtn = $x("//a[contains(@class,'navbar__auth_login')]");
    private final SelenideElement imgProfile = $x("//img[@class='navbar__profile-img']");


    @Step("Open main page - stepik.org")
    public MainPage openMainPage() {
        open("https://stepik.org/");
        return this;
    }

    @Step("Click on Log In Button")
    public AuthPage clickOnLogIn() {
        logInBtn.click();
        return new AuthPage();
    }


    public boolean imgProfileIsExist() {
        return imgProfile.shouldBe(Condition.visible).isDisplayed();
    }


}
