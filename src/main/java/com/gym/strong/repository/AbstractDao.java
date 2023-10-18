package com.gym.strong.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao<T> {
    private final Map<Long, T> dataMap = new HashMap<>();
    private long nextId = 1;

    public void save(T entity) {
        long entityId = getNextId();
        setId(entity, entityId);
        dataMap.put(entityId, entity);
    }

    public T getById(Long id) {
        return dataMap.get(id);
    }

    public List<T> getAll() {
        return new ArrayList<>(dataMap.values());
    }

    public List<T> getAllIn(List<Long> ids) {
        List<T> list = new ArrayList<>();

        for (Long id : ids) {
            list.add(getById(id));
        }

        return list;
    }

    public void delete(Long id) {
        dataMap.remove(id);
    }

    private synchronized long getNextId() {
        return nextId++;
    }

    protected abstract void setId(T entity, long id);
}
