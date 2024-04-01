package ui.browser;

import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.SelenideErrorFormatter;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import ui.annotations.BrowserRunTypes;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static ui.annotations.BrowserRunTypes.*;

public class BrowserTypeProcessing implements BeforeAllCallback, BeforeEachCallback {

    private final ExtensionContext.Namespace space = ExtensionContext.Namespace.create(BrowserTypeProcessing.class);
    private final String remoteUrl = "http://localhost:4444/wd/hub";

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        BrowserRunTypes classAnnotation = context.getRequiredTestClass().getAnnotation(BrowserRunTypes.class);
        context.getStore(space).put("classAnnotation", classAnnotation);
    }


    private Capabilities getBrowserCapabilities(Browsers browser) {
        switch (browser) {
            case CHROME -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("browserVersion", "122.0");
                chromeOptions.addArguments("start-maximized");
                chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
                return chromeOptions;
            }
            case FIREFOX -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("browserVersion", "117.0");
                firefoxOptions.addArguments("--start-maximized");
                firefoxOptions.addArguments("–disable-popup-blocking");
                return firefoxOptions;
            }
            case EDGE -> {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setCapability("browserVersion", "122.0");
                return edgeOptions;
            }
            default -> throw new IllegalArgumentException("Bad browser type");
        }
    }

    private DesiredCapabilities setSelenoidCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", true);
        desiredCapabilities.setCapability("selenoid:options", selenoidOptions);
        return desiredCapabilities;
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        BrowserRunTypes classAnnotation = context.getStore(space).get("classAnnotation", BrowserRunTypes.class);
        BrowserRunTypes methodAnnotation = context.getRequiredTestMethod().getAnnotation(BrowserRunTypes.class);

        if (methodAnnotation == null) {
            methodAnnotation = classAnnotation;
        }

        if (methodAnnotation != null)
            if (methodAnnotation.isRemote()) {
                DesiredCapabilities desiredCapabilities = setSelenoidCapabilities();
                desiredCapabilities.merge(getBrowserCapabilities(methodAnnotation.browser()));
                RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(remoteUrl), desiredCapabilities);
                remoteWebDriver.setFileDetector(new LocalFileDetector());
                WebDriverRunner.setWebDriver(remoteWebDriver);
            } else {
                SelenideConfig selenideConfig = new SelenideConfig().browser(methodAnnotation.browser().name().toLowerCase());
                SelenideDriver driver = new SelenideDriver(selenideConfig);
                WebDriverRunner.setWebDriver(driver.getAndCheckWebDriver());
                WebDriverRunner.getWebDriver().manage().window().maximize();
            }
    }


}
