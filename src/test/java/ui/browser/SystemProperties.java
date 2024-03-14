package ui.browser;

import ui.annotations.BrowserRunTypes;
import ui.annotations.BrowserRunTypes.Browsers;

import java.util.Optional;

public final class SystemProperties {

    //public static final Browser BROWSER = getBrowser();
    //public static final boolean ISREMOTE = getRemoteProperty();

    public static boolean getRemoteProperty() {
        if (System.getProperty("remote") != null) {
            return Boolean.parseBoolean(System.getProperty("remote"));
        }
        return false;
    }

    public static BrowserRunTypes.Browsers getBrowser() {
        String propertyBrowser = Optional.of(System.getProperty("browser")).orElse("CHROME").toUpperCase();
        switch (Browsers.valueOf(propertyBrowser)) {
            case CHROME -> {
                return Browsers.CHROME;
            }
            case FIREFOX -> {
                return Browsers.FIREFOX;
            }
            case EDGE -> {
                return Browsers.EDGE;
            }
            case OPERA -> {
                return Browsers.OPERA;
            }
        }
        return Browsers.CHROME;
    }
}
