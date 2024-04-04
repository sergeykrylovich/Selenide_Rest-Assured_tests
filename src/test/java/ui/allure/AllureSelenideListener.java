package ui.allure;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.logging.Level;

public class AllureSelenideListener implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .enableLogs(LogType.BROWSER, Level.ALL));
    }
}
