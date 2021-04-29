package xyz.mlhmz.rapidTestApp.database.dao;

import xyz.mlhmz.rapidTestApp.database.EntityManagerUtil;
import xyz.mlhmz.rapidTestApp.database.entities.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tests implements DAO {
    private static final Logger logger = Logger.getLogger(Tests.class.getName());

    @Override
    public Object create(Object o) {
        if (o instanceof Test) {
            Test test = (Test) o;
            EntityManager em = EntityManagerUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(test);
            em.getTransaction().commit();
            return test;
        } else {
            logger.log(Level.SEVERE, "There was a wrong Object given at the Create Method!");
            return null;
        }
    }

    @Override
    public ArrayList<Object> get() {
        EntityManager em = EntityManagerUtil.getEntityManager();
        return new ArrayList<Object>(em.createQuery("from Test", Test.class).getResultList());
    }

    @Override
    public Object getById(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        Test test = em.find(Test.class, id);
        return test;
    }

    @Override
    public void update(Long id, Object o) {
        if (o instanceof Test) {
            Test updated = (Test) o;
            EntityManager em = EntityManagerUtil.getEntityManager();
            Test test = em.find(Test.class, id);

            em.getTransaction().begin();

            // No update for the Test Date needed.

            if (updated.getPersonId() != null) {
                test.setPersonId(updated.getPersonId());
            }

            em.getTransaction().commit();


        } else {
            logger.log(Level.SEVERE, "There was a wrong Object given at the Update Method!");
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = EntityManagerUtil.getEntityManager();
        Test test = em.find(Test.class, id);
        em.getTransaction().begin();
        em.remove(test);
        em.getTransaction().commit();
    }
}
