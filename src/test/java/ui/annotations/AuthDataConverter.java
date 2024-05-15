package ui.annotations;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Convert;
import ui.injects.AuthDataParametrConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ConvertWith(AuthDataParametrConverter.class)
public @interface AuthDataConverter {
}
