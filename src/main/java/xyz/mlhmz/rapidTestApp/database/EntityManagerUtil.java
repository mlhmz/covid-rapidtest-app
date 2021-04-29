package xyz.mlhmz.rapidTestApp.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
    public static final String unitName = "rapidTestApp";

    public static EntityManager entityManager;

    private EntityManagerUtil() {

    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unitName);

            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }
}
