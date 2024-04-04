package ui.allure;

import com.codeborne.selenide.Selenide;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class AllureAttachments {

    private static final String SELENOID_HOST = System.getenv("host").replaceAll("/wd/hub", "");


/*    @Attachment(value = "Page Source", type = "text/plain")
    public static byte[] getPageSource() {
        return WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }
    @Attachment(value = "Page screen", type = "img/png")
    public static byte[] getPageScreen() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }*/

/*    @Attachment(value = "Browser Logs", type = "text/plain")
    public static String getLogs() {
        String browser = ((RemoteWebDriver) WebDriverRunner.getWebDriver()).getCapabilities().getBrowserName();
        if (browser.equals("chrome")) {
            return String.join("\n", Selenide.getWebDriverLogs(LogType.BROWSER.name()));
        }
        return null;
    }*/

/*    @Attachment(value = "Video HTML", type = "text/html", fileExtension = ".html")
    public static String getVideoURL(String sessionId) {
        String url = "http://localhost:4444/video/" + sessionId + ".mp4";
        return "<html><body><a href=\"" + url + "\">" + url + "</a><video width='100%' height='400px' controls autoplay><source src='"
                + url + "' type='video/mp4'></video></body></html>";
    }*/

    @Attachment(value = "Video", type = "video/mp4", fileExtension = ".mp4")
    public static byte[] attachVideo(String sessionId) {
        Selenide.closeWebDriver();
        try {
            File mp4 = new File(System.getProperty("java.io.tmpdir") + "temp.mp4");
            mp4.deleteOnExit();
            Thread.sleep(3000);
            FileUtils.copyURLToFile(new URL(getVideoUrl(sessionId)), mp4);
            return Files.toByteArray(mp4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private static String getVideoUrl(String sessionId) {
        return SELENOID_HOST + "/video/" + sessionId + ".mp4";
    }

}
