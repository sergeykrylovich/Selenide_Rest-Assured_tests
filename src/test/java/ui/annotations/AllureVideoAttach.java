package ui.annotations;

import org.junit.jupiter.api.extension.ExtendWith;
import ui.allure.AllureVideoExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(AllureVideoExtension.class)
public @interface AllureVideoAttach {
}
