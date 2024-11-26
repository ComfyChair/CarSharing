package carsharing.dbutil;

import java.util.List;

public interface DataAccessObject<T> {
    List<T> findAll();
    T findById(int id);
    void add(T object);
    void update(T object);
    void deleteById(int id);
}
