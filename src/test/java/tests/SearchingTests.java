package tests;

import com.codeborne.selenide.*;
import com.codeborne.selenide.collections.TextsInAnyOrder;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.Conditions;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;
import ui.pages.CoursePage;
import ui.pages.MainPage;
import ui.annotations.BrowserRunTypes;
import ui.pages.SearchingPage;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static ui.enums.Languages.*;

@Epic("Test for searching content")
@BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME, isRemote = false)
public class SearchingTests {


    private MainPage mainPage;

/*    @BeforeAll
    public static void setUp() {
        MainPage authorizePage = new MainPage();
        authorizePage.openMainPage().authorizeThroughAPI();
    }*/

    @BeforeEach
    public void setUpPage() {
        mainPage = new MainPage();
    }


    @Test
    @Tag("UI")
    @Feature("searching course")
    @DisplayName("Search java course")
    public void searchJavaCourseTest() {
        String expectedSearchingQuery = "java";
        ElementsCollection resultLinks = mainPage.openMainPage()
                .inputSearchingData(expectedSearchingQuery)
                .ChooseLanguage(RUSSIAN)
                .chooseWithCertificatesCheckBox()
                .chooseFreeCursesCheckBox()
                .clickOnSearchingBtn()
                .getResultLinks();

        List<String> collect = resultLinks.asFixedIterable().stream().map(x -> x.getText()).collect(Collectors.toList());

        assertThat(collect).anySatisfy(x -> x.contains("java"));
    }

    @Test
    @Tag("UI")
    @Feature("searching course and open it")
    @DisplayName("Search java course and open it")
    public void searchJavaCourseAndOpenTest() {
        String expectedSearchingQuery = "java";
        CoursePage coursePage = new CoursePage();
        mainPage.openMainPage()
                .inputSearchingData(expectedSearchingQuery)
                .ChooseLanguage(RUSSIAN)
                .chooseWithCertificatesCheckBox()
                .chooseFreeCursesCheckBox()
                .clickOnSearchingBtn()
                .clickOnFirstLinkOfSearchingResult();

        Selenide.switchTo().window(1);
        SelenideElement headerName = coursePage.getHeaderName();
        assertThat(headerName.getText()).containsIgnoringCase(expectedSearchingQuery);

    }
}
