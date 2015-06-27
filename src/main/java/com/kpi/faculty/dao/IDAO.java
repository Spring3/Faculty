package com.kpi.faculty.dao;

import java.util.List;


//Basic CRUD interface
public interface IDAO <T> {
    List<T> getAll();
    T get(String name);
    boolean create(T value);
    boolean remove(T value);
    boolean update(T value);
}
