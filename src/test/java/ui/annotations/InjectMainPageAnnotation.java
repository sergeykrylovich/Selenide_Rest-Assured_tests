package ui.annotations;


import org.junit.jupiter.api.extension.ExtendWith;
import ui.injects.InjectMainPage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(InjectMainPage.class)
public @interface InjectMainPageAnnotation {
}
