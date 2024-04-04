package tests;

import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ui.allure.AllureVideoExtension;
import ui.annotations.AllureVideoAttach;
import ui.annotations.SelenideListener;
import ui.pages.CoursePage;
import ui.pages.MainPage;
import ui.annotations.BrowserRunTypes;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static ui.annotations.BrowserRunTypes.Browsers.*;
import static ui.enums.Languages.*;

@SelenideListener
@AllureVideoAttach
@BrowserRunTypes(browser = CHROME, isRemote = false)
@Epic("Tests for searching content")
//@BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME, isRemote = false)
public class SearchingTests {


    private MainPage mainPage;

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
                .clickOnLinkByNumberInSearchingResult(1);

        Selenide.switchTo().window(1);
        SelenideElement headerName = coursePage.getHeaderName();
        assertThat(headerName.getText()).containsIgnoringCase(expectedSearchingQuery);
    }



    @Test
    @Tag("UI")
    @Tag("test1")
    @Feature("Searching content")
    @DisplayName("Searching for not-existing course")
    public void searchingForNonExistingCourseTest() {
        String expectedSearchingQuery = "dfgfhwse4ywvsfghsdffbg";
        CoursePage coursePage = new CoursePage();
        String message = mainPage.openMainPage()
                .inputSearchingData(expectedSearchingQuery)
                .ChooseLanguage(ALL)
                .clickOnSearchingBtn()
                .getNotFoundCourseMessage();

        assertThat(message).contains(expectedSearchingQuery).contains("ничего не найдено");
    }

    @AfterEach
    public void cleanUp() {
        WebDriverRunner.getWebDriver().quit();
    }
}
