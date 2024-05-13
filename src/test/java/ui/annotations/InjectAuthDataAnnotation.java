package ui.annotations;

import org.junit.jupiter.api.extension.ExtendWith;
import ui.injects.InjectAuthData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@ExtendWith(InjectAuthData.class)
public @interface InjectAuthDataAnnotation {
}
