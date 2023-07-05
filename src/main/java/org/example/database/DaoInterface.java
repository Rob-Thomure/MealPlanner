package org.example.database;

import java.util.List;

public interface DaoInterface<T extends DataTransferObject>  {
    void createTable();
    T findById(int id);
    List<T> findAll();
    void update(T dto);
    void create(T dto);
    void delete(T dto);
}
