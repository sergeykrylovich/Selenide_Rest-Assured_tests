package ui.pages;

import api.pojo.AuthorizationData;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AuthPage {

    private final SelenideElement logIn = $x("//button[contains(@class,'sign-form__btn')]");
    private final SelenideElement emailInput = $(By.id("id_login_email"));
    private final SelenideElement passwordInput = $("#id_login_password");


    @Step("Fill email and password in log in form")
    public AuthPage fillEmailAndPassword(String email, String password) {
        emailInput.setValue(email);
        passwordInput.sendKeys(password);
        return this;
    }

    @Step("Fill email and password in log in form by AuthData Instance")
    public AuthPage fillEmailAndPassword(AuthorizationData authorizationData) {
        emailInput.setValue(authorizationData.getEmail());
        passwordInput.sendKeys(authorizationData.getPassword());
        return this;
    }

    @Step("Click on log in button")
    public MainPage submitCredentialsAndLogin() {
        logIn.click();
        return new MainPage();
    }

}
