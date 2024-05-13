package ui.injects;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import ui.pages.MainPage;

import javax.inject.Inject;
import java.lang.reflect.Field;

public class InjectMainPage implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Field[] fields = testInstance.getClass().getDeclaredFields();

        for (Field field : fields) {
           if (field.isAnnotationPresent(Inject.class) && field.getType().isAssignableFrom(MainPage.class)) {
               field.setAccessible(true);
               field.set(testInstance, new MainPage());
           }
        }
    }
}
