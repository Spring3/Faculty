package com.kpi.faculty.dao;

import java.util.List;

/**
 * Created by user on 6/26/2015.
 */
public interface IDAO <T> {
    List<T> getAll();
    T get(String name);
    boolean create(T value);
    boolean remove(T value);
    boolean update(T value);
}
