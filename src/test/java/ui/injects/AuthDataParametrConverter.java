package ui.injects;

import api.pojo.AuthorizationData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import tests.AuthorizationTests;
import ui.enums.AuthDataType;

import static org.assertj.core.api.Assertions.assertThat;
import static ui.enums.AuthDataType.VALID;

public class AuthDataParametrConverter implements ArgumentConverter {

    @Override
    public AuthorizationData convert(Object source, ParameterContext context) throws ArgumentConversionException {
        assertThat(source.getClass()).isEqualTo(AuthDataType.class);
        assertThat(context.getParameter().getType()).isEqualTo(AuthorizationData.class);

        if (source == VALID) {
            return new AuthorizationData(AuthorizationTests.EMAIL, AuthorizationTests.PASSWORD);
        } else {
            throw  new IllegalArgumentException("argument must be VALID");
        }

    }
}
