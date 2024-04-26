package db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;

import static java.lang.System.currentTimeMillis;

public class JpaExtension implements BeforeAllCallback {
    private static final Logger log = LoggerFactory.getLogger(JpaExtension.class);

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).getOrComputeIfAbsent(JpaExtensionCallback.class);
    }

    static class JpaExtensionCallback implements ExtensionContext.Store.CloseableResource {
        @Override
        public void close() {
            //перебираем все подключения к БД
            for (EntityManagerFactory emf : EmfContext.INSTANCE.storedEmf()) {
                //если подключение не пустое и все еще открыто
                if (emf != null && emf.isOpen()) {
                    long start = currentTimeMillis();
                    //логируем время отключения от БД
                    log.info("Close start on " + start);
                    //закрываем подключение
                    emf.close();
                }
            }
        }
    }
}
