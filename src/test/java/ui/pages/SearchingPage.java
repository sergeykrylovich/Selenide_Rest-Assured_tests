package ui.pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SearchingPage {

    private final ElementsCollection resultLinks = $$(By.xpath("//a[@class='course-card__title']"));
    private final SelenideElement notFoundCourseMessage = $(".catalog__search-results-message");


    @Step("Get result list of searching")
    public ElementsCollection getResultLinks() {
        return resultLinks.shouldHave(CollectionCondition.sizeGreaterThan(1));
    }

    @Step("Click on first link of searching result")
    public CoursePage clickOnLinkByNumberInSearchingResult(int numberOfLink) {
        if ((numberOfLink - 1) < 0) {
            throw new RuntimeException("Number of link cant be less that 0");
        }
        resultLinks.shouldHave(CollectionCondition.sizeGreaterThan(1)).get(numberOfLink - 1).click();
        return new CoursePage();
    }

    @Step("Return message if course not found")
    public String getNotFoundCourseMessage() {
        return notFoundCourseMessage.shouldBe(visible).getText();

    }
}
