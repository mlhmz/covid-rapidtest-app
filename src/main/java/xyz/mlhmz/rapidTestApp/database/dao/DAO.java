package xyz.mlhmz.rapidTestApp.database.dao;

import xyz.mlhmz.rapidTestApp.database.EntityManagerUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public interface DAO {
    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    // Creates an Entity by just giving an Object, an ID is created automatically.
    Object create(Object o);

    // Get every Object from the Table and return it as ArrayList
    ArrayList<Object> get();

    // Find an Entity by its id
    Object getById(Long id);

    // Update an Entity, find it by its ID and then apply the given Values from the Object
    void update(Long id, Object o);

    // Delete an Enitty by its id
    void delete(Long id);
}
