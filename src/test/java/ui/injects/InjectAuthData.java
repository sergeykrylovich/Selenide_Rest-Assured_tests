package ui.injects;

import api.pojo.AuthorizationData;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import tests.AuthorizationTests;

import static tests.AuthorizationTests.*;

public class InjectAuthData implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(AuthorizationData.class);
    }

    @Override
    public AuthorizationData resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new AuthorizationData(EMAIL, PASSWORD);
    }
}
