package xyz.mlhmz.rapidTestApp.database.repositories;

import xyz.mlhmz.rapidTestApp.database.entities.Person;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Persons implements Repository {
    private static final Logger logger = Logger.getLogger(Persons.class.getName());

    @Override
    public Object create(Object o) {
        if (o instanceof Person) {
            Person person = (Person) o;
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
            return person;
        } else {
            logger.log(Level.SEVERE, "There was a wrong Object given at the Create Method!");
            return null;
        }
    }

    @Override
    public ArrayList<Object> get() {
        return new ArrayList<Object>(entityManager.createQuery("from Person", Person.class).getResultList());
    }

    @Override
    public Object getById(Long id) {
        Person person = entityManager.find(Person.class, id);
        return person;
    }

    @Override
    public void update(Long id, Object o) {
        if (o instanceof Person) {
            Person updated = (Person) o;
            Person person = entityManager.find(Person.class, id);

            entityManager.getTransaction().begin();

            if (updated.getFirstName() != null) {
                person.setFirstName(updated.getFirstName());
            }

            if (updated.getLastName() != null) {
                person.setLastName(updated.getLastName());
            }

            if (updated.getAddress() != null) {
                person.setAddress(updated.getAddress());
            }

            if (updated.getAddress() != null) {
                person.setAddress(updated.getAddress());
            }

            if (updated.getPhoneNumber() != null) {
                person.setPhoneNumber(updated.getPhoneNumber());
            }


            entityManager.getTransaction().commit();


        } else {
            logger.log(Level.SEVERE, "There was a wrong Object given at the Update Method!");
        }
    }

    @Override
    public void delete(Long id) {
        Person person = entityManager.find(Person.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(person);
        entityManager.getTransaction().commit();
    }
}
