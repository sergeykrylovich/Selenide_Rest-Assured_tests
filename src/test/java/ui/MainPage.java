package ui;

import api.AuthService;
import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import ui.enums.Languages;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    public static final String MAIN_PAGE_URL = "https://stepik.org/";
    public final String FAVICON_URL = "https://stepik.org/static/classic/ico/favicon.svg";

    private final SelenideElement logInBtn = $x("//a[contains(@class,'navbar__auth_login')]");
    private final SelenideElement imgProfile = $x("//img[@class='navbar__profile-img']");
    private final SelenideElement searchingInput = $x("//input[@class='search-form__input ']");
    private final SelenideElement searchingBtn = $x("//button[@class='button_with-loader search-form__submit']");
    private final SelenideElement withCertificatesCheckBox = $x("//span[contains(text(),'С сертификатами')]");
    private final SelenideElement freeCursesCheckBox = $x("//span[text()='Бесплатные']");
    private final SelenideElement dropDownLanguage = $x("//div[contains(@class,'select-box__dropdown')]");
    private final SelenideElement russianLanguage = $x("//li[contains(@class, 'select-box-item')]//span[text()='На русском']");
    private final SelenideElement englishLanguage = $x("//li[contains(@class, 'select-box-item')]//span[text()='На английском']");
    private final SelenideElement allLanguages = $x("//li[contains(@class, 'select-box-item')]//span[text()='На любом языке']");
    private final ElementsCollection languagesList = $$x("//li[contains(@class, 'select-box-item')]//span[@class='select-box-option__content']");


    @Step("Open main page - stepik.org")
    public MainPage openMainPage() {
        open(MAIN_PAGE_URL);
        return this;
    }

    @Step("Open favicon url for set cookies")
    public MainPage openFaviconPage() {
        open(FAVICON_URL);
        return this;
    }

    @Step("Click on Log In Button")
    public AuthPage clickOnLogIn() {
        logInBtn.click(ClickOptions.withTimeout(Duration.ofSeconds(10)));
        return new AuthPage();
    }
    @Step("Click on searching input and enter a searching query")
    public MainPage inputSearchingData(String searchingQuery) {
        searchingInput.clear();
        searchingInput.sendKeys(searchingQuery);
        return this;
    }

    @Step("Click on drop down menu and choose language")
    public MainPage ChooseLanguage(Languages language) {
        dropDownLanguage.click();
        switch (language) {
            case ENGLISH:
                englishLanguage.click();
                break;
            case RUSSIAN:
                russianLanguage.click();
                break;
            case ALL:
                allLanguages.click();
                break;
        }
        return this;
    }

    @Step("Click on 'With certificates' check box")
    public MainPage chooseWithCertificatesCheckBox() {
        withCertificatesCheckBox.click();
        return this;
    }

    @Step("Click on 'Free' check box")
    public MainPage chooseFreeCursesCheckBox() {
        freeCursesCheckBox.click();
        return this;
    }

    @Step("Click on searching button")
    public MainPage clickOnSearchingBtn() {
        searchingBtn.click();
        return this;
    }


    public boolean imgProfileIsExist() {
        return imgProfile.shouldBe(Condition.visible).isDisplayed();
    }

    public MainPage authorizeThroughAPI() {
        AuthService authService = new AuthService();
        Set<Cookie> cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        Map<String, String> responseCookies = authService.getAuthTokens(cookies);
        setCookies(responseCookies);
        Selenide.refresh();
        return this;
    }

    public void setCookies(Map<String, String> cookies) {
        int i = 0;
        for (Map.Entry<String, String> cookie : cookies.entrySet()) {
            Cookie tempCookie = new Cookie(cookie.getKey(), cookie.getValue(), "stepik.org", "/", null);
            WebDriverRunner.getWebDriver().manage().addCookie(tempCookie);
        }
    }


}
