package kz.yerkebulan.repository;

import java.util.List;

public interface Repository<T> {
    void create(T entity);
    T findById(int id);
    List<T> findAll();
    void update(int id, T entity);
    void delete(int id);
}
