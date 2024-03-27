package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import ui.MainPage;
import ui.annotations.BrowserRunTypes;

import static ui.enums.Languages.*;

@Epic("Test for searching content")
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

    @BrowserRunTypes
    @Test
    @Tag("UI")
    @Feature("searching")
    @DisplayName("Search java course")
    public void searchJavaCourseTest() throws InterruptedException {
        String expectedSearchingQuery = "java";
        mainPage.openMainPage()
                .inputSearchingData(expectedSearchingQuery)
                .ChooseLanguage(RUSSIAN)
                .chooseWithCertificatesCheckBox()
                .chooseFreeCursesCheckBox()
                .clickOnSearchingBtn();

        Thread.sleep(3000);
    }
}
