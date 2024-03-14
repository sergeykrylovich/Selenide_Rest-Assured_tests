package ui.annotations;


import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.devtools.CdpEndpointFinder;
import ui.browser.BrowserTypeProcessing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@ExtendWith(BrowserTypeProcessing.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BrowserRunTypes {

    Browsers browser() default Browsers.CHROME;

    boolean isRemote() default false;

    enum Browsers {
        CHROME, FIREFOX, EDGE, OPERA
    }
}
