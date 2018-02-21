package org.dev.krishna.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    public List<T> findAll();
    public Optional<T> find(String id);
    public String add(T object) throws Exception ;
    public void update(T object) throws Exception ;
    public void delete(String id) throws Exception ;
}
