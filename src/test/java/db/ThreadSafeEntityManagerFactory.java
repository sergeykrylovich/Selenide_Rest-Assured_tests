package db;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.util.Map;

import static javax.persistence.SynchronizationType.SYNCHRONIZED;

public class ThreadSafeEntityManagerFactory implements EntityManagerFactory {


    private final EntityManagerFactory entityManagerFactory;
    private final ThreadLocal<EntityManager> entityManagerThreadLocal;

    public ThreadSafeEntityManagerFactory(EntityManagerFactory entityManagerFactory, ThreadLocal<EntityManager> entityManagerThreadLocal) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManagerThreadLocal = ThreadLocal.withInitial(entityManagerFactory::createEntityManager);
    }

    public boolean isCurrentEmContainsAllProperties(Map map) {
        final EntityManager em = entityManagerThreadLocal.get();
        return em.getProperties().keySet().containsAll(map.keySet()) &&
                em.getProperties().entrySet().stream()
                        .filter(x -> map.containsKey(x.getKey()))
                        .allMatch(e -> e.getValue().equals(map.get(e.getKey())));
    }

    @Override
    public EntityManager createEntityManager() {
        return entityManagerThreadLocal.get();
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        if (!isCurrentEmContainsAllProperties(map)) {
            entityManagerThreadLocal.set(entityManagerFactory.createEntityManager(map));
        }
        return entityManagerThreadLocal.get();
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        if (synchronizationType == SYNCHRONIZED) {
            return this.createEntityManager();
        } else return entityManagerFactory.createEntityManager(synchronizationType);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        if (synchronizationType == SYNCHRONIZED) {
            return this.createEntityManager(map);
        } else return entityManagerFactory.createEntityManager(synchronizationType, map);
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return entityManagerFactory.getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return entityManagerFactory.getMetamodel();
    }

    @Override
    public boolean isOpen() {
        return entityManagerFactory.isOpen();
    }

    @Override
    public void close() {
        entityManagerFactory.close();
    }

    @Override
    public Map<String, Object> getProperties() {
        return entityManagerFactory.getProperties();
    }

    @Override
    public Cache getCache() {
        return entityManagerFactory.getCache();
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return entityManagerFactory.getPersistenceUnitUtil();
    }

    @Override
    public void addNamedQuery(String name, Query query) {
        entityManagerFactory.addNamedQuery(name, query);
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return entityManagerFactory.unwrap(cls);
    }

    @Override
    public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
        entityManagerFactory.addNamedEntityGraph(graphName, entityGraph);
    }
}
