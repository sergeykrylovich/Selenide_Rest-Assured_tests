package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CoursePage {

    private final SelenideElement headerName = $x("//h1[@class='course-promo__header']");

    public SelenideElement getHeaderName() {
        return headerName.shouldBe(Condition.visible);
    }
}
