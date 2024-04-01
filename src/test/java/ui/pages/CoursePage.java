package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CoursePage {

    private final SelenideElement headerName = $x("//h1[@class='course-promo__header']");


    public SelenideElement getHeaderName() {
        return headerName.shouldBe(Condition.visible);
    }


}
