package ui.pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SearchingPage {

    private final ElementsCollection resultLinks = $$(By.xpath("//a[@class='course-card__title']"));


    @Step("Get result list of searching")
    public ElementsCollection getResultLinks() {
        return resultLinks.shouldHave(CollectionCondition.sizeGreaterThan(1));
    }

    @Step("Click on first link of searching result")
    public CoursePage clickOnFirstLinkOfSearchingResult() {
        resultLinks.shouldHave(CollectionCondition.sizeGreaterThan(1)).get(0).click();
        return new CoursePage();
    }
}
