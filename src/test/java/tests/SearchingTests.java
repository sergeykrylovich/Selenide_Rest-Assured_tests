package tests;

import com.codeborne.selenide.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import ui.pages.CoursePage;
import ui.pages.MainPage;
import ui.annotations.BrowserRunTypes;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static ui.enums.Languages.*;

@Epic("Tests for searching content")
//@BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME, isRemote = false)
public class SearchingTests {


    private MainPage mainPage;

    @BeforeAll
    public static void setUp() {
        int i = 0;
    }

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


    @BrowserRunTypes(browser = BrowserRunTypes.Browsers.CHROME)
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
