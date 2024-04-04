package listeners;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ui.annotations.SelenideListener;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FailListener implements AfterTestExecutionCallback {

    private static final Set<String> failedTestNames = new HashSet<>();

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String methodName = context.getRequiredTestMethod().getName();
        String className = context.getRequiredTestClass().getName();
        String testName = String.format("--tests %s.%s*", className, methodName);
        context.getExecutionException().ifPresent(x -> failedTestNames.add(testName));
    }

    @SneakyThrows
    public static void savedFailTest() {
        String path = System.getProperty("user.dir" + "/src/test/resources/FailedTests.txt");
        String resultTestString = String.join(" ", failedTestNames);
        FileUtils.writeStringToFile(new File(path), resultTestString);
    }

}
