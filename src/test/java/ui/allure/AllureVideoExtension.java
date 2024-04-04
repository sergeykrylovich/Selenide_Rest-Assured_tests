package ui.allure;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AllureVideoExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        context.getExecutionException().ifPresent(x -> {
            AllureAttachments.attachVideo(Selenide.sessionId().toString());
        });

    }
}
